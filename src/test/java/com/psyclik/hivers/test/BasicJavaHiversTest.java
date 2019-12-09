package com.psyclik.hivers.test;

import com.psyclik.hivers.Hivers;
import com.psyclik.hivers.aspect.AfterInvocation;
import com.psyclik.hivers.aspect.AroundInvocation;
import com.psyclik.hivers.aspect.BeforeInvocation;
import com.psyclik.hivers.aspect.ExecutionContext;
import com.psyclik.hivers.provider.Prototype;
import com.psyclik.hivers.provider.Singleton;
import com.psyclik.hivers.scope.AnyScope;
import kotlin.Unit;

import java.lang.reflect.Method;
import java.util.function.Function;

public class BasicJavaHiversTest {

    public static Method getMethod(final Class<?> objectClass, final String methodName) {
        try {
            return objectClass.getMethod(methodName);
        } catch (NoSuchMethodException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void testBasicHiversUseCase() throws InterruptedException {
        final Hivers hivers = new Hivers();

        // Add a provider to the scope, of type prototype, that binds on Nested.class.
        hivers.provider(Nested.class, new Prototype<>(() -> new Nested(hivers.instanceOf(TestService.class))));

        // Shortcut for singleton addition.
        hivers.bean(TestService.class, new TestServiceImpl());

        // Shortcurt for singleton, binding on the element class.
        hivers.bean(new Nested(hivers.instanceOf(TestService.class)));

        // Stacks a scope.
        hivers.scope(new AnyScope(), scope -> {

            // Adds a singleton with aspects
            scope.bean(TestService.class, new TestServiceBlipImpl(),

                    // Define AoP behaviour on the before(Pong)
                    new BeforeInvocation(getMethod(TestService.class, "pong"), ExecutionContext -> {
                        System.out.println("before >> ");
                        return Unit.INSTANCE; // Kotlin compatibility.
                    }),

                    // Define AoP behaviour around calls to the pong method.
                    new AroundInvocation(getMethod(TestService.class, "pong"), (Function<ExecutionContext, Object>) ctx -> {

                        try {
                            final long before = System.nanoTime();
                            final Object result;
                            result = ctx.proceed();
                            System.out.println("Method " + ctx.method.getName() + " executed in " + (System.nanoTime() - before / 1000000.0));
                            return result;
                        } catch (final Exception exception) {
                            throw new RuntimeException(exception);
                        }
                    }),

                    // Adds behaviour after the calls to pong.
                    new AfterInvocation(getMethod(TestService.class, "pong"), ctx -> {
                        System.out.println("<< after!");
                        return Unit.INSTANCE; // Kotlin compatibility.
                    })
            );

            return Unit.INSTANCE; // Kotlin compatibility.
        });

        // Define AoP behaviour around calls to the pong method.
        hivers.provider(Nested.class, new Singleton<>(new Nested(hivers.instanceOf(TestService.class))));
        hivers.provider(Nested.class, new Prototype<>(() -> new Nested(hivers.instanceOf(TestService.class))));

        // Test call
        final TestService testService = hivers.instanceOf(TestService.class);
        for (int i = 0; i < 5; i++) {
            testService.ping();
        }
        Thread.sleep(5000000);
    }

}
