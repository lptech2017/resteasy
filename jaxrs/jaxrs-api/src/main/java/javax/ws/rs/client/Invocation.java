/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2011 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * http://glassfish.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */
package javax.ws.rs.client;

import java.util.Locale;
import java.util.Set;
import java.util.concurrent.Future;

import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.RequestHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.TypeLiteral;

/**
 * TODO javadoc.
 *
 * @author Marek Potociar
 */
public interface Invocation {

    /**
     * TODO javadoc.
     */
    public static interface Builder extends SyncInvoker {

        // Invocation builder methods
        // TODO: document that the request instance needs to be cloned so that the
        // data used in the invocation processing chain are decoupled from the original
        // request data that were used to initiate the invocation to prevent accidental
        // issues caused by mutable nature of the request
        /**
         * Build a request invocation using an arbitrary request method name.
         *
         * @param method request method name.
         * @return invocation encapsulating the built request.
         */
        public Invocation build(String method);

        /**
         * Build a request invocation using an arbitrary request method name and
         * request entity.
         *
         * @param method request method name.
         * @param entity request entity.
         * @return invocation encapsulating the built request.
         */
        public Invocation build(String method, Entity<?> entity);

        /**
         * Build a GET request invocation.
         *
         * @return invocation encapsulating the built GET request.
         */
        public Invocation buildGet();

        /**
         * Build a DELETE request invocation.
         *
         * @return invocation encapsulating the built DELETE request.
         */
        public Invocation buildDelete();

        /**
         * Build a POST request invocation.
         *
         * @param entity request entity
         * @return invocation encapsulating the built POST request.
         */
        public Invocation buildPost(Entity<?> entity);

        /**
         * Build a PUT request invocation.
         *
         * @param entity request entity
         * @return invocation encapsulating the built PUT request.
         */
        public Invocation buildPut(Entity<?> entity);

        /**
         * Access the asynchronous uniform request invocation interface to
         * asynchronously invoke the built request.
         *
         * @return asynchronous uniform request invocation interface.
         */
        public AsyncInvoker async();

        /**
         * Add acceptable languages.
         *
         * @param locales an array of the acceptable languages
         * @return the updated builder.
         */
        public Builder acceptLanguage(Locale... locales);

        /**
         * Add acceptable languages.
         *
         * @param locales an array of the acceptable languages
         * @return the updated builder.
         */
        public Builder acceptLanguage(String... locales);

        /**
         * Add a cookie to be set.
         *
         * @param cookie to be set.
         * @return the updated builder.
         */
        public Builder cookie(Cookie cookie);

        /**
         * Set the list of allowed methods for the resource. Any duplicate method
         * names will be truncated to a single entry.
         *
         * @param methods the methods to be listed as allowed for the resource,
         *     if {@code null} any existing allowed method list will be removed.
         * @return the updated builder.
         */
        public Builder allow(String... methods);

        /**
         * Set the list of allowed methods for the resource.
         *
         * @param methods the methods to be listed as allowed for the resource,
         *     if {@code null} any existing allowed method list will be removed.
         * @return the updated builder.
         */
        public Builder allow(Set<String> methods);

        /**
         * Set the cache control data of the message.
         *
         * @param cacheControl the cache control directives, if {@code null}
         *     any existing cache control directives will be removed.
         * @return the updated builder.
         */
        public Builder cacheControl(CacheControl cacheControl);

        /**
         * Add an arbitrary header.
         *
         * @param name the name of the header
         * @param value the value of the header, the header will be serialized
         *     using a {@link javax.ws.rs.ext.RuntimeDelegate.HeaderDelegate} if
         *     one is available via {@link javax.ws.rs.ext.RuntimeDelegate#createHeaderDelegate(java.lang.Class)}
         *     for the class of {@code value} or using its {@code toString} method
         *     if a header delegate is not available. If {@code value} is {@code null}
         *     then all current headers of the same name will be removed.
         * @return the updated builder.
         */
        public Builder header(String name, Object value);

        /**
         * Replaces all existing headers with the newly supplied headers.
         *
         * @param headers new headers to be set, if {@code null} all existing
         *     headers will be removed.
         * @return the updated builder.
         */
        public Builder headers(RequestHeaders headers);

        /**
         * Get access to the underlying {@link Configuration configuration}.
         *
         * @return a mutable configuration bound to the instance.
         */
        public Configuration configuration();
    }

    /**
     * Synchronously invoke the request and receive a response back.
     *
     * @return {@link Response response} object as a result of the request invocation.
     * @throws InvocationException in case the invocation failed.
     */
    public Response invoke() throws InvocationException;

    /**
     * Synchronously invoke the request and receive a response of the specified type back.
     *
     * @param <T> response type
     * @param responseType Java type the response should be converted into.
     * @return response object of the specified type as a result of the request invocation.
     * @throws InvocationException in case the invocation failed.
     */
    public <T> T invoke(Class<T> responseType) throws InvocationException;

    /**
     * Synchronously invoke the request and receive a response of the specified generic type back.
     *
     * @param <T> generic response type
     * @param responseType type literal representing a generic Java type the response should be converted into.
     * @return response object of the specified generic type as a result of the request invocation.
     * @throws InvocationException in case the invocation failed.
     */
    public <T> T invoke(TypeLiteral<T> responseType) throws InvocationException;

    /**
     * Submit the request for an asynchronous invocation and receive a future response back.
     *
     * @return future {@link Response response} object as a result of the request invocation.
     */
    public Future<Response> submit();

    /**
     * Submit the request for an asynchronous invocation and receive a future response of the specified type back.
     *
     * @param <T> response type
     * @param responseType Java type the response should be converted into.
     * @return future response object of the specified type as a result of the request invocation.
     */
    public <T> Future<T> submit(Class<T> responseType);

    /**
     * Submit the request for an asynchronous invocation and receive a future response of the specified generic type back.
     *
     * @param <T> generic response type
     * @param responseType type literal representing a generic Java type the response should be converted into.
     * @return future response object of the specified generic type as a result of the request invocation.
     */
    public <T> Future<T> submit(TypeLiteral<T> responseType);

    /**
     * Submit the request for an asynchronous invocation and register an {@link InvocationCallback}
     * to process the future result of the invocation.
     *
     * @param <T> response type
     * @param callback invocation callback for asynchronous processing of the request invocation result.
     * @return future response object of the specified type as a result of the request invocation.
     */
    public <T> Future<T> submit(InvocationCallback<T> callback);

    /**
     * Get access to the underlying {@link Configuration configuration}.
     *
     * @return a mutable configuration bound to the instance.
     */
    public Configuration configuration();
}
