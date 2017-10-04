import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';
import { enableProdMode } from '@angular/core';
import { environment } from '../environments/environment';
import { Module } from './app/module';

if ( environment.production ) 
{
    enableProdMode();
}

platformBrowserDynamic().bootstrapModule( Module );