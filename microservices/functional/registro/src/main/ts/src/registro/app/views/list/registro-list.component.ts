import { Broker } from 'eits-ng2';
import { Component, OnInit, Input, Output, ViewContainerRef } from '@angular/core';
import { DecimalPipe } from '@angular/common';
import { MdDialog, MdSnackBar } from '@angular/material';
import { TdDialogService, IPageChangeEvent } from '@covalent/core';


@Component({
  selector: 'registro-list',
  templateUrl: './registro-list.component.html'
})
export class RegistroListComponent implements OnInit {

  @Input()
  registro: any = {};

  filtro: any = {};

  categorias: any[] = [];
  categoria: any;
  registros: any[] = [];

  page: any;
  
  public pageSizes: number[] = [20,19,18,17,16,15,14,13,12,11,10,9,8,7,6,5,4,3,2,1];

  tipos = [
    { value: null, viewValue: null },
    { value: 'RECEITA', viewValue: 'RECEITA' },
    { value: 'DESPESA', viewValue: 'DESPESA' }
  ];

  meses = [
    { value: '1', viewValue: 'janeiro' },
    { value: '2', viewValue: 'fevereiro' },
    { value: '3', viewValue: 'março' },
    { value: '4', viewValue: 'abril' },
    { value: '5', viewValue: 'maio' },
    { value: '6', viewValue: 'junho' },
    { value: '7', viewValue: 'julho' },
    { value: '8', viewValue: 'agosto' },
    { value: '9', viewValue: 'setembro' },
    { value: '10', viewValue: 'outubro' },
    { value: '11', viewValue: 'novembro' },
    { value: '12', viewValue: 'dezembro' }
  ];

  public pageable = {
    size: 5,
    page: 0,
    sort: {
      orders: [{
        direction: "DESC",
        property: "valor",
        nullHandlingHint: "NATIVE"
      }]
    }
  };

  constructor(public dialog: MdDialog, private _dialogService: TdDialogService,
    private _viewContainerRef: ViewContainerRef, public snackBar: MdSnackBar) {

  }

  ngOnInit(): void {
    this.listRegistroById(null, null, null, this.pageable);
    this.listCategoriaByFiltersDesativada(null, null);
  }



  public listRegistroById = function (mes, ano, categoria, pageable) {
    Broker.of("registroService").promise("listRegistroById", mes, ano, categoria, pageable)
      .then((result) => {
        this.registros = result.content;
        this.page = result;
      })
      .catch((exception) => {
        console.log(exception.message);
      })
  }

  public listCategoriaByFiltersDesativada = function (nome, pageable) {
    Broker.of("registroService").promise("listCategoriaByFiltersDesativada", nome, pageable)
      .then((result) => {
        this.categorias = result.content;
      })
      .catch((exception) => {
        console.log(exception.message);
      })
  }



  public removeRegistro = function (id) {
    this._dialogService.openConfirm({
      message: 'Você realmente deseja remover este registro?',
      cancelButton: 'Cancelar',
      acceptButton: 'Sim'
    }).afterClosed().subscribe((accept: boolean) => {
      if (accept) {
        Broker.of("registroService").promise("removeRegistro", id)
          .then((result) => {
            //this.openAlert('Registro removido com sucesso!');
            this.openSnackBar("Registro removido com sucesso!");
            this.listRegistroById(null, null);
          })
          .catch((exception) => {
            this.openAlert(exception.message);
          })
      } else { }
    })
  }

  public filtrar = function () {
    this.listRegistroById(this.filtro.mes, this.filtro.ano, null, this.pageable)
  }

  public changePage(event: IPageChangeEvent): void {
    this.pageable.page = (event.page - 1);
    this.pageable.size = (event.pageSize);
    this.filtrar();

  }

  openAlert(mensagem: String): void{
    this._dialogService.openAlert({
        title: "Registro",    
        message: (""+ mensagem)
    
    });
}
openSnackBar(messagem: string) {
  this.snackBar.open(messagem, "Ok", {
      duration: 5000,
  });
}

  displayCategoria(categoria): String {
    return categoria ? categoria.nome : "";
  }

}

