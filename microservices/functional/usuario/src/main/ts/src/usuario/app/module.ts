import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CovalentCommonModule, CovalentLayoutModule } from '@covalent/core';

import { RoutingModule } from './routing.module';

import { UsuarioView } from './views/usuario/usuario-view.component';

/**
 * 
 */
@NgModule( {
    declarations: [
        UsuarioView,
    ],
    imports: [
        CovalentCommonModule,
        CovalentLayoutModule,
        BrowserModule,
        FormsModule,
        RoutingModule
    ],
    providers: [],
    bootstrap: [UsuarioView]
})
export class Module 
{
    /*-------------------------------------------------------------------
     *                           ATTRIBUTES
     *-------------------------------------------------------------------*/
}
