import { Broker } from 'eits-ng2';
import { Component, HostBinding, AfterViewInit, OnInit } from '@angular/core';
import { TdMediaService } from '@covalent/core';
import { NgModel } from '@angular/forms'


@Component({
    selector: 'categoria-view',
    templateUrl: './categoria-view.component.html'
})
export class CategoriaViewComponent implements OnInit{
  
  categorias: any[]=[];

  constructor(){

  }

  tipos = [
    { value: null, viewValue: null },
    { value: 'RECEITA', viewValue: 'RECEITA' },
    { value: 'DESPESA', viewValue: 'DESPESA' }    
  ];

  ngOnInit(): void {
    
    this.listCategoriaByFiltersFull(null,null,null, null);
    
  }
    

    public listCategoriaByFiltersFull = function (tipo,nome,descricao, pageable){


      Broker.of("categoriaService").promise("listCategoriaByFiltersFull", tipo, nome, descricao, pageable).then((result) =>{
        this.categorias = result.content;
      })
      .catch((exception)=> {
        console.log(exception.message);
      });
    }




  }
    


