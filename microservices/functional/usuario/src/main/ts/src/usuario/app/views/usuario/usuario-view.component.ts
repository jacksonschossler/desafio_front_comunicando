import { Component } from '@angular/core';
import { Broker } from 'eits-ng2';

/**
 * 
 */
@Component( {
    selector: 'app-root',
    templateUrl: './usuario-view.component.html',
})
export class UsuarioView 
{
    /*-------------------------------------------------------------------
     *                           ATTRIBUTES
     *-------------------------------------------------------------------*/
    /**
     * 
     */
    private usuario:any = {};

    /*-------------------------------------------------------------------
     *                           BEHAVIORS
     *-------------------------------------------------------------------*/
    /**
     * 
     */
    public onFindUserById():void
    {
        Broker.of("usuarioService").promise("findUsuarioById", 1)
            .then( (result) => {
                console.log(result);
                this.usuario = result;
            })
            .catch( (message) => {
                console.error(message);
            }
        );
    }
}
