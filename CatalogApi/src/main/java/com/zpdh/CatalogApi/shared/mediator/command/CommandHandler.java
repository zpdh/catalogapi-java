package com.zpdh.CatalogApi.shared.mediator.command;

/**
 * Handler de comandos.
 *
 * @param <C> Tipo do comando.
 * @param <R> Tipo do resultado esperado.
 */
public interface CommandHandler<C extends Command<R>, R> {
    R handle(C command);
}
