import { Broker } from 'eits-ng2';
import { Component, OnInit, Input, Output, ViewContainerRef } from '@angular/core';
import { DecimalPipe } from '@angular/common';
import { MdDialog, MdSnackBar } from '@angular/material';
import { TdDialogService } from '@covalent/core';


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

  constructor(public dialog: MdDialog, private _dialogService: TdDialogService,
    private _viewContainerRef: ViewContainerRef, public snackBar: MdSnackBar) {

  }

  ngOnInit(): void {
    this.listRegistroById(null, null, null, null);
    this.listCategoriaByFiltersDesativada(null, null);
  }



  public listRegistroById = function (mes, ano, categoria, pageable) {
    Broker.of("registroService").promise("listRegistroById", mes, ano, categoria, pageable)
      .then((result) => {
        this.registros = result.content;
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
    this.listRegistroById(this.filtro.mes, this.filtro.ano, null, null)
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

