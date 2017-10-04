import { Component, Inject } from "@angular/core";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  menus: Object[] = [{
    icon: 'home',
    route: './categoria',
    title: 'Início',
  },
  {
    icon: 'add',
     route: './categoria/form',
    title: 'Criar nova categoria',
  },
  {
    icon: 'view_headline',
    route: '/categoria/list',
    title: 'Listar categorias'
  },
  // {
  //   icon: 'details',
  //   route: '/categoria/detail',
  //   title: 'Detalhes da categoria'
  // }
  ];
  // usermenu: Object[] = [{
  //   icon: 'exit_to_app',
  //   // route: '.',
  //   title: 'Sair da conta',
  // }
  // ];

}
