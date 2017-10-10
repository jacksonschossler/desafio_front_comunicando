import { Broker } from 'eits-ng2';
import { Component, OnInit, Input, Output } from '@angular/core';
import { DecimalPipe } from '@angular/common';


@Component({
    selector: 'registro-list',
    templateUrl: './registro-list.component.html'
})
export class RegistroListComponent implements OnInit{

    @Input()
    registro: any ={};

    filtro: any = {};

    categorias: any[] = [];
    categoria: any;
    registros: any[] = [];

    tipos = [
        { value: null, viewValue: null },
        { value: 'RECEITA', viewValue: 'RECEITA' },
        { value: 'DESPESA', viewValue: 'DESPESA' }    
      ];

    meses = [
        {value: '1', viewValue: 'janeiro'},
        {value: '2', viewValue: 'fevereiro'},
        {value: '3', viewValue: 'março'},
        {value: '4', viewValue: 'abril'},
        {value: '5', viewValue: 'maio'},
        {value: '6', viewValue: 'junho'},
        {value: '7', viewValue: 'julho'},
        {value: '8', viewValue: 'agosto'},
        {value: '9', viewValue: 'setembro'},
        {value: '10', viewValue: 'outubro'},
        {value: '11', viewValue: 'novembro'},
        {value: '12', viewValue: 'dezembro'}
    ];

    ngOnInit(): void {
        this.listRegistroById(null, null, null, null);
        this.listCategoriaByFiltersDesativada(null,null);
    }



    public listRegistroById = function (mes, ano, categoria, pageable) {
        Broker.of("registroService").promise("listRegistroById", mes, ano, categoria, pageable)
        .then((result) =>{
            this.registros = result.content;
        })
        .catch((exception)=> {
            console.log(exception.message);
        })
    }

    public listCategoriaByFiltersDesativada = function (nome, pageable)
    {
        Broker.of("registroService").promise("listCategoriaByFiltersDesativada", nome, pageable)
        .then((result)=> {
            this.categorias = result.content;
        })
        .catch((exception)=> {
            console.log(exception.message);
        })
    }



    public filtrar = function(){
        this.listRegistroById(this.filtro.mes, this.filtro.ano, null, null)
    }

    displayCategoria(categoria): String {
        return categoria ? categoria.nome : "";
    }

}

