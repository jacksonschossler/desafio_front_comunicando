import { NgModule, ModuleWithProviders } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
	{
		path: '',
		redirectTo: '/',
		pathMatch: 'full'
	},
];

export const routing: ModuleWithProviders = RouterModule.forRoot(routes, { useHash: true });

/**
 *
 */
@NgModule({
    imports: [ routing ],
    exports: [ RouterModule ]
})
export class RoutingModule
{

}

export const appRoutingProviders: any[] = [];
