import { Broker } from 'eits-ng2';
import { Component, HostBinding, AfterViewInit, OnInit, Input, Output, EventEmitter, ViewContainerRef } from '@angular/core';
import { TdMediaService, TdDialogService, IPageChangeEvent } from '@covalent/core';
import { NgModel } from '@angular/forms'
import { MdDialog, MdSnackBar } from '@angular/material';
import { RemoveConfirmComponent } from './../../controls/remove-confirm.component';


@Component({
  selector: 'categoria-view',
  templateUrl: './categoria-view.component.html'
})
export class CategoriaViewComponent implements OnInit {

  categorias: any[] = [];

  @Input()
  categoria: any;

  filtro: any = {};

  page: any;

  public pageSizes: number[] = [20,19,18,17,16,15,14,13,12,11,10,9,8,7,6,5,4,3,2,1];

  sizes: [5, 10, 15]; 


  public pageable = {
    size: 5,
    page: 0,
    sort: {
      orders: [{
        direction: "DESC",
        property: "nome",
        nullHandlingHint: "NATIVE"
      }]
    }
  };

  tipos = [
    { value: null, viewValue: null },
    { value: 'RECEITA', viewValue: 'RECEITA' },
    { value: 'DESPESA', viewValue: 'DESPESA' }
  ];

  @Output()
  onRemoveCategoria: EventEmitter<any> = new EventEmitter;

  constructor(public dialog: MdDialog, private _dialogService: TdDialogService,
    private _viewContainerRef: ViewContainerRef, public snackBar: MdSnackBar) {

  }

  ngOnInit(): void {

    this.listCategoriaByFiltersFull(null, null, null, this.pageable);

  }


  public listCategoriaByFiltersFull = function (tipo, nome, descricao, pageable) {

    Broker.of("categoriaService").promise("listCategoriaByFiltersFull", tipo, nome, descricao, pageable).then((result) => {
      this.categorias = result.content;
      this.page = result;
    })
      .catch((exception) => {
        console.log(exception.message);
      });
  }


  public filtrar = function () {
    this.listCategoriaByFiltersFull(this.filtro.tipo, this.filtro.nome, this.filtro.descricao, this.pageable)
  }

  openDialogRemoveConfirm(): void {
    let dialogRef = this.dialog.open(RemoveConfirmComponent, {
      width: '300px',
      data: { id: this.categoria.id }
    });
    dialogRef.afterClosed().subscribe(categoriaId => {
      if (categoriaId) {
        this.categoria = categoriaId;
        this.onRemoveCategoria.emit(categoriaId);
      }
    });
  }


  public updateCategoriaToDesativada = function (id) {
    this._dialogService.openConfirm({
      message: 'Você realmente deseja ativar/desativar esta categoria?',
      cancelButton: 'Cancelar',
      acceptButton: 'Sim'
    }).afterClosed().subscribe((accept: boolean) => {
      if (accept) {
        Broker.of("categoriaService").promise("updateCategoriaToDesativada", id)
          .then((result) => {
            //this.openAlert("Categoria alterada sucesso!");
            this.openSnackBar("Categoria alterada com sucesso!")
            this.listCategoriaByFiltersFull(null, null, null, null);
          })
          .catch((exception) => {
            this.openAlert(exception.message);
          })
      } else {

      }
    })
  }


  public removeCategoria = function (id) {
    this._dialogService.openConfirm({
      message: 'Você realmente deseja remover esta categoria?',
      cancelButton: 'Cancelar',
      acceptButton: 'Sim'
    }).afterClosed().subscribe((accept: boolean) => {
      if (accept) {
        Broker.of("categoriaService").promise("removeCategoria", id)
          .then((result) => {
            //this.openAlert("Categoria deletada com sucesso!");
            this.openSnackBar("Categoria deletada com sucesso!")
            this.listCategoriaByFiltersFull(null, null, null, null);
          })
          .catch((exception) => {
            this.openAlert(exception.message);
          })
      } else {

      }
    })
  }

  public changePage(event: IPageChangeEvent): void {
    this.pageable.page = (event.page - 1);
    this.pageable.size = (event.pageSize);
    this.filtrar();
  }
 

  openAlert(mensagem: String): void {
    this._dialogService.openAlert({
      title: "Categoria",
      message: ("" + mensagem)

    });
  }

  openSnackBar(messagem: string) {
    this.snackBar.open(messagem, "Ok", {
      duration: 5000,
    });
  }


  openConfirm(): void {
    this._dialogService.openConfirm({
      message: 'Você realmente deseja remover esta categoria?',
      cancelButton: 'Cancelar',
      acceptButton: 'Sim'
    }).afterClosed().subscribe((accept: boolean) => {
      if (accept) {
        this.openSnackBar("Categoria deletada com sucesso!");
      } else {

      }
    })
  }

 


}



