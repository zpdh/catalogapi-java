package com.zpdh.CatalogApi.shared.mediator;

import com.zpdh.CatalogApi.shared.mediator.command.Command;
import com.zpdh.CatalogApi.shared.mediator.query.Query;

public interface Mediator {
    <R> R send(Command<R> command);

    <R> R query(Query<R> query);
}
