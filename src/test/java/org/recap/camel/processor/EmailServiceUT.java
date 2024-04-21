package org.recap.camel.processor;

import org.apache.camel.*;
import org.apache.camel.clock.Clock;
import org.apache.camel.spi.UnitOfWork;
import org.apache.camel.trait.message.MessageTrait;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.recap.BaseTestCaseUT;
import org.recap.PropertyKeyConstants;
import org.recap.ScsbCommonConstants;
import org.recap.util.CommonUtil;
import org.recap.util.PropertyUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by sudhishk on 19/1/17.
 */
public class EmailServiceUT extends BaseTestCaseUT {

    @InjectMocks
    EmailService emailService;

    @Mock
    private ProducerTemplate producerTemplate;

    @Mock
    PropertyUtil propertyUtil;

    @Mock
    CommonUtil commonUtil;

    @Value("${" + PropertyKeyConstants.SCSB_EMAIL_ASSIST_TO + "}")
    String recapSupportEmailTo;

    @Test
    public void testRecalEmail() {
            ReflectionTestUtils.setField(emailService,"recapSupportEmailTo",recapSupportEmailTo);
            List<String> institutionCodes= Arrays.asList(ScsbCommonConstants.NYPL,ScsbCommonConstants.COLUMBIA,ScsbCommonConstants.PRINCETON);
            Mockito.when(commonUtil.findAllInstitutionCodesExceptSupportInstitution()).thenReturn(institutionCodes);
            Mockito.when(propertyUtil.getPropertyByInstitutionAndKey(Mockito.anyString(),Mockito.anyString())).thenReturn("test@email.com");
            Exchange exchange = getExchange(ScsbCommonConstants.NYPL);
            emailService.setInstitutionCode(ScsbCommonConstants.NYPL);
            emailService.sendEmailForMatchingReports(exchange);
            emailService.sendEmailForAccessionReports(exchange);
            emailService.sendEmailForMatchingCGDReports(exchange);
            assertEquals(ScsbCommonConstants.NYPL,emailService.getInstitutionCode());
    }


    private Exchange getExchange(String names) {
        Exchange exchange=new Exchange() {


            @Override
            public ExchangePattern getPattern() {
                return null;
            }

            @Override
            public void setPattern(ExchangePattern pattern) {

            }

            @Override
            public Object getProperty(ExchangePropertyKey key) {
                return null;
            }

            @Override
            public <T> T getProperty(ExchangePropertyKey key, Class<T> type) {
                return null;
            }

            @Override
            public <T> T getProperty(ExchangePropertyKey key, Object defaultValue, Class<T> type) {
                return null;
            }

            @Override
            public void setProperty(ExchangePropertyKey key, Object value) {

            }

            @Override
            public Object removeProperty(ExchangePropertyKey key) {
                return null;
            }

            @Override
            public Object getProperty(String name) {
                return null;
            }

            @Override
            public <T> T getProperty(String name, Class<T> type) {
                return null;
            }

            @Override
            public <T> T getProperty(String name, Object defaultValue, Class<T> type) {
                return null;
            }

            @Override
            public void setProperty(String name, Object value) {

            }

            @Override
            public Object removeProperty(String name) {
                return null;
            }

            @Override
            public boolean removeProperties(String pattern) {
                return false;
            }

            @Override
            public boolean removeProperties(String pattern, String... excludePatterns) {
                return false;
            }

            @Override
            public Map<String, Object> getProperties() {
                return null;
            }

            @Override
            public Map<String, Object> getAllProperties() {
                return null;
            }

            @Override
            public boolean hasProperties() {
                return false;
            }

            @Override
            public Object getVariable(String name) {
                return null;
            }

            @Override
            public <T> T getVariable(String name, Class<T> type) {
                return null;
            }

            @Override
            public <T> T getVariable(String name, Object defaultValue, Class<T> type) {
                return null;
            }

            @Override
            public void setVariable(String name, Object value) {

            }

            @Override
            public Object removeVariable(String name) {
                return null;
            }

            @Override
            public Map<String, Object> getVariables() {
                return Map.of();
            }

            @Override
            public boolean hasVariables() {
                return false;
            }

            @Override
            public Message getIn() {
                return new Message() {
                    @Override
                    public void reset() {

                    }

                    @Override
                    public String getMessageId() {
                        return null;
                    }

                    @Override
                    public long getMessageTimestamp() {
                        return 0;
                    }

                    @Override
                    public void setMessageId(String messageId) {

                    }

                    @Override
                    public boolean hasMessageId() {
                        return false;
                    }

                    @Override
                    public Exchange getExchange() {
                        return null;
                    }

                    @Override
                    public Object getHeader(String name) {
                        return names;
                    }

                    @Override
                    public Object getHeader(String name, Object defaultValue) {
                        return null;
                    }

                    @Override
                    public Object getHeader(String name, Supplier<Object> defaultValueSupplier) {
                        return null;
                    }

                    @Override
                    public <T> T getHeader(String name, Class<T> type) {
                        return null;
                    }

                    @Override
                    public <T> T getHeader(String name, Object defaultValue, Class<T> type) {
                        return null;
                    }

                    @Override
                    public <T> T getHeader(String name, Supplier<Object> defaultValueSupplier, Class<T> type) {
                        return null;
                    }

                    @Override
                    public void setHeader(String name, Object value) {

                    }

                    @Override
                    public Object removeHeader(String name) {
                        return null;
                    }

                    @Override
                    public boolean removeHeaders(String pattern) {
                        return false;
                    }

                    @Override
                    public boolean removeHeaders(String pattern, String... excludePatterns) {
                        return false;
                    }

                    @Override
                    public Map<String, Object> getHeaders() {
                        return null;
                    }

                    @Override
                    public void setHeaders(Map<String, Object> headers) {

                    }

                    @Override
                    public boolean hasHeaders() {
                        return false;
                    }

                    @Override
                    public Object getBody() {
                        return null;
                    }

                    @Override
                    public Object getMandatoryBody() throws InvalidPayloadException {
                        return null;
                    }

                    @Override
                    public <T> T getBody(Class<T> type) {
                        return null;
                    }

                    @Override
                    public <T> T getMandatoryBody(Class<T> type) throws InvalidPayloadException {
                        return null;
                    }

                    @Override
                    public void setBody(Object body) {

                    }

                    @Override
                    public <T> void setBody(Object body, Class<T> type) {

                    }

                    @Override
                    public Message copy() {
                        return null;
                    }

                    @Override
                    public void copyFrom(Message message) {

                    }

                    @Override
                    public void copyFromWithNewBody(Message message, Object newBody) {

                    }

                    @Override
                    public boolean hasTrait(MessageTrait trait) {
                        return false;
                    }

                    @Override
                    public Object getPayloadForTrait(MessageTrait trait) {
                        return null;
                    }

                    @Override
                    public void setPayloadForTrait(MessageTrait trait, Object object) {

                    }
                };
            }

            @Override
            public Message getMessage() {
                return null;
            }

            @Override
            public <T> T getMessage(Class<T> type) {
                return null;
            }

            @Override
            public void setMessage(Message message) {

            }

            @Override
            public <T> T getIn(Class<T> type) {
                return null;
            }

            @Override
            public void setIn(Message in) {

            }

            @Override
            public Message getOut() {
                return null;
            }

            @Override
            public <T> T getOut(Class<T> type) {
                return null;
            }

            @Override
            public boolean hasOut() {
                return false;
            }

            @Override
            public void setOut(Message out) {

            }

            @Override
            public Exception getException() {
                return null;
            }

            @Override
            public <T> T getException(Class<T> type) {
                return null;
            }

            @Override
            public void setException(Throwable t) {

            }

            @Override
            public boolean isFailed() {
                return false;
            }

            @Override
            public boolean isTransacted() {
                return false;
            }

            @Override
            public boolean isRouteStop() {
                return false;
            }

            @Override
            public void setRouteStop(boolean routeStop) {

            }

            @Override
            public boolean isExternalRedelivered() {
                return false;
            }

            @Override
            public boolean isRollbackOnly() {
                return false;
            }

            @Override
            public void setRollbackOnly(boolean rollbackOnly) {

            }

            @Override
            public boolean isRollbackOnlyLast() {
                return false;
            }

            @Override
            public void setRollbackOnlyLast(boolean rollbackOnlyLast) {

            }

            @Override
            public CamelContext getContext() {
                return null;
            }

            @Override
            public Exchange copy() {
                return null;
            }

            @Override
            public Endpoint getFromEndpoint() {
                return null;
            }

            @Override
            public String getFromRouteId() {
                return null;
            }

            @Override
            public UnitOfWork getUnitOfWork() {
                return null;
            }

            @Override
            public String getExchangeId() {
                return null;
            }

            @Override
            public void setExchangeId(String id) {

            }

            @Override
            public long getCreated() {
                return 0;
            }

            @Override
            public ExchangeExtension getExchangeExtension() {
                return null;
            }

            @Override
            public Clock getClock() {
                return null;
            }
        };
        return exchange;
    }
}
