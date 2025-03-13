/*
 * Copyright 2012-2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.data.solr.repository.query;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.ScrollPosition;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.DefaultParameters;
import org.springframework.data.repository.query.ParametersParameterAccessor;
import org.springframework.data.repository.util.QueryExecutionConverters;
import org.springframework.data.repository.util.ReactiveWrapperConverters;

import java.util.Iterator;

/**
 * Implementation of {@link SolrParameterAccessor}
 *
 * @author Christoph Strobl
 * @author Mark Paluch
 */
public class SolrParametersParameterAccessor implements SolrParameterAccessor {

	private final DefaultParameters parameters;
	private final ParametersParameterAccessor parametersParameterAccessorDelegate;
	private final Object[] values;


	public SolrParametersParameterAccessor(SolrQueryMethod solrQueryMethod, Object[] values) {
		this.parameters = solrQueryMethod.getParameters();
		this.parametersParameterAccessorDelegate = new ParametersParameterAccessor(this.parameters, values.clone());
		if (requiresUnwrapping(values)) {
			this.values = new Object[values.length];

			for (int i = 0; i < values.length; i++) {
				this.values[i] = QueryExecutionConverters.unwrap(values[i]);
			}
		} else {
			this.values = values;
		}
	}

	private static boolean requiresUnwrapping(Object[] values) {

		for (Object value : values) {
			if (value != null && (QueryExecutionConverters.supports(value.getClass())
					|| ReactiveWrapperConverters.supports(value.getClass()))) {
				return true;
			}
		}

		return false;
	}

	@Override
	public float getBoost(int index) {
		return parameters.getBindableParameter(index).getBoost();
	}

	@Override
	public Pageable getPageable() {
		return parametersParameterAccessorDelegate.getPageable();
	}

	@Override
	public Sort getSort() {
		return parametersParameterAccessorDelegate.getSort();
	}

	@Override
	public Object getBindableValue(int index) {
		return parametersParameterAccessorDelegate.getBindableValue(index);
	}

	@Override
	public boolean hasBindableNullValue() {
		return parametersParameterAccessorDelegate.hasBindableNullValue();
	}

	@Override
	public Iterator<Object> iterator() {
		return new BindableSolrParameterIterator(parametersParameterAccessorDelegate.iterator());
	}

/*	@Override
	public Optional<Class<?>> getDynamicProjection() {
		return parametersParameterAccessorDelegate.getDynamicProjection();
	}*/

	@Override
	public Class<?> findDynamicProjection() {
		return parametersParameterAccessorDelegate.findDynamicProjection();
	}

	public class BindableSolrParameterIterator implements Iterator<Object> {

		private final Iterator<Object> delegate;
		private int currentIndex = 0;

		public BindableSolrParameterIterator(Iterator<Object> delegate) {
			this.delegate = delegate;
		}

		@Override
		public boolean hasNext() {
			return delegate.hasNext();
		}

		@Override
		public BindableSolrParameter next() {
			BindableSolrParameter solrParameter = new BindableSolrParameter(currentIndex, delegate.next());
			solrParameter.setBoost(parameters.getBindableParameter(currentIndex).getBoost());
			currentIndex++;
			return solrParameter;
		}

		@Override
		public void remove() {
			delegate.remove();
		}

	}

	@Override
	public ScrollPosition getScrollPosition() {

		if (!parameters.hasScrollPositionParameter()) {

			Pageable pageable = getPageable();
			if (pageable.isPaged()) {
				return pageable.toScrollPosition();
			}

			return null;
		}

		return (ScrollPosition) values[parameters.getScrollPositionIndex()];
	}

	/**
	 * Returns the potentially unwrapped values.
	 *
	 * @return
	 */
	public Object[] getValues() {
		return this.values;
	}

}
