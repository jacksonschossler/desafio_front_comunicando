import { NgModule, ModuleWithProviders } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AppComponent } from './app.component';
import { CategoriaViewComponent} from './views/categoria/categoria-view.component';
import { CategoriaFormComponent} from './views/form/categoria-form.component';
import { CategoriaDetailComponent} from './views/detail/categoria-detail.component';

const routes: Routes = [

		{path: 'categoria',
		children: [
			{path: 'list', component :CategoriaViewComponent},
			{path: 'form', component :CategoriaFormComponent},
			{path: 'detail', component :CategoriaDetailComponent},
			{path: 'edit/:id', component :CategoriaFormComponent}
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
