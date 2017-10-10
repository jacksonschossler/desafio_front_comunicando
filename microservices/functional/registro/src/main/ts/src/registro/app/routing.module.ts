import { NgModule, ModuleWithProviders } from '@angular/core';
import { AppComponent } from './app.component';
import { RouterModule, Routes } from '@angular/router';
import { RegistroFormComponent } from './views/form/registro-form.component';
import { RegistroListComponent } from './views/list/registro-list.component';
import { RegistroChartComponent } from './views/chart/registro-chart.component';

const routes: Routes = [
	{
		path: 'registro',
		children:[
			{path: 'list', component :RegistroListComponent},
			{path: 'form', component :RegistroFormComponent},
			{path: 'chart', component :RegistroChartComponent},
			{path: 'edit/:id', component :RegistroFormComponent}
		]
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
