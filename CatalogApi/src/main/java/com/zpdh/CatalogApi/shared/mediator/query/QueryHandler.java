package com.zpdh.CatalogApi.shared.mediator.query;

/**
 * Handler de queries.
 *
 * @param <Q> Tipo da query.
 * @param <R> Tipo do resultado esperado.
 */
public interface QueryHandler<Q extends Query<R>, R> {
    R handle(Q query);
}
