import { Component, Inject } from "@angular/core";

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html'
  })

export class AppComponent {

    menus: Object[] = [{
        icon: 'home',
        route: './registro',
        title: 'Início',
      },
      {
        icon: 'add',
         route: './registro/form',
        title: 'Criar novo registro',
      },
      {
        icon: 'view_headline',
        route: '/registro/list',
        title: 'Listar registros'
      },
      {
        icon: 'insert_chart',
        route: '/registro/chart',
        title: 'Grafico de registros'
      }
      ];
    
}