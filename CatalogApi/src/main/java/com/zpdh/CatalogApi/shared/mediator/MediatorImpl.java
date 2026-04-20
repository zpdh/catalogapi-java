package com.zpdh.CatalogApi.shared.mediator;

import com.zpdh.CatalogApi.shared.mediator.command.Command;
import com.zpdh.CatalogApi.shared.mediator.command.CommandHandler;
import com.zpdh.CatalogApi.shared.mediator.query.Query;
import com.zpdh.CatalogApi.shared.mediator.query.QueryHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ResolvableType;
import org.springframework.stereotype.Component;

@Component
public class MediatorImpl implements Mediator {
    private final ApplicationContext context;

    public MediatorImpl(ApplicationContext context) {
        this.context = context;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <R> R send(Command<R> command) {
        Class<?> resultType = ResolvableType.forInstance(command)
            .as(Command.class)
            .getGeneric(0)
            .resolve();

        if (resultType == null) {
            throw new IllegalStateException("Could not result result type for command: " + command.getClass().getSimpleName());
        }

        ResolvableType handlerType = ResolvableType.forClassWithGenerics(
            CommandHandler.class,
            command.getClass(),
            resultType
        );

        String[] handlerNames = context.getBeanNamesForType(handlerType);

        if (handlerNames.length == 0) {
            throw new IllegalStateException("No handler found for command: " + command.getClass().getSimpleName());
        }

        CommandHandler<Command<R>, R> handler = (CommandHandler<Command<R>, R>) context.getBean(handlerNames[0]);

        return handler.handle(command);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <R> R query(Query<R> query) {
        Class<?> resultType = ResolvableType.forInstance(query)
            .as(Query.class)
            .getGeneric(0)
            .resolve();

        if (resultType == null) {
            throw new IllegalStateException("Could not result result type for query: " + query.getClass().getSimpleName());
        }

        ResolvableType handlerType = ResolvableType.forClassWithGenerics(
            QueryHandler.class,
            query.getClass(),
            resultType
        );

        String[] handlerNames = context.getBeanNamesForType(handlerType);

        if (handlerNames.length == 0) {
            throw new IllegalStateException("No handler found for query: " + query.getClass().getSimpleName());
        }

        QueryHandler<Query<R>, R> handler = (QueryHandler<Query<R>, R>) context.getBean(handlerNames[0]);

        return handler.handle(query);
    }
}
