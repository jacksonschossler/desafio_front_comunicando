import { Broker } from 'eits-ng2';
import { Component, HostBinding, AfterViewInit, OnInit, Input, Output, EventEmitter, ViewContainerRef } from '@angular/core';
import { TdMediaService, TdDialogService } from '@covalent/core';
import { NgModel } from '@angular/forms'
import { MdDialog } from '@angular/material';
import { RemoveConfirmComponent } from './../../controls/remove-confirm.component';


@Component({
    selector: 'categoria-view',
    templateUrl: './categoria-view.component.html'
})
export class CategoriaViewComponent implements OnInit{
  
  categorias: any[]=[];

  @Input()
  categoria: any;

  filtro: any = {};

  tipos = [
    { value: null, viewValue: null },
    { value: 'RECEITA', viewValue: 'RECEITA' },
    { value: 'DESPESA', viewValue: 'DESPESA' }    
  ];
  
  @Output()
  onRemoveCategoria: EventEmitter<any> = new EventEmitter;

  constructor(public dialog: MdDialog, private _dialogService: TdDialogService,
              private _viewContainerRef: ViewContainerRef){
    
      }

  ngOnInit(): void {
    
    this.listCategoriaByFiltersFull(null,null,null,null);
    
  }
    

    public listCategoriaByFiltersFull = function (tipo,nome,descricao, pageable){

      Broker.of("categoriaService").promise("listCategoriaByFiltersFull", tipo, nome, descricao, pageable).then((result) =>{
        this.categorias = result.content;
      })
      .catch((exception)=> {
        console.log(exception.message);
      });
    }

  

    public filtrar = function(){
      this.listCategoriaByFiltersFull(this.filtro.tipo, this.filtro.nome, this.filtro.descricao, this.filtro.pageable)
    }

    openDialogRemoveConfirm():void{
      let dialogRef = this.dialog.open(RemoveConfirmComponent, {
        width: '300px',
        data: {id: this.categoria.id}
      });
      dialogRef.afterClosed().subscribe(categoriaId =>{
        if(categoriaId){
          this.categoria = categoriaId;
          this.onRemoveCategoria.emit(categoriaId);
        }
      });
    }


    public updateCategoriaToDesativada = function(id){
      this._dialogService.openConfirm({
        message: 'Você realmente deseja ativar/desativar esta categoria?',
        cancelButton: 'Cancelar',
        acceptButton: 'Sim'
      }).afterClosed().subscribe((accept: boolean)=>{
        if (accept) {
          Broker.of("categoriaService").promise("updateCategoriaToDesativada", id)
          .then((result) => {
              this.openAlert("Categoria alterada sucesso!");
              this.listCategoriaByFiltersFull(null,null,null,null);
          })
          .catch((exception) =>{
              this.openAlert(exception.message);
          })
        } else{
          
        }
      })
  }


    public removeCategoria = function(id){
      this._dialogService.openConfirm({
        message: 'Você realmente deseja remover esta categoria?',
        cancelButton: 'Cancelar',
        acceptButton: 'Sim'
      }).afterClosed().subscribe((accept: boolean)=>{
        if (accept) {
          Broker.of("categoriaService").promise("removeCategoria", id)
          .then((result) => {
              this.openAlert("Categoria deletada com sucesso!");
              this.listCategoriaByFiltersFull(null,null,null,null);
          })
          .catch((exception) =>{
              this.openAlert(exception.message);
          })
        } else{
          
        }
      })
  }


  openAlert(mensagem: String): void{
    this._dialogService.openAlert({
        title: "Categoria",    
        message: (""+ mensagem)
    
    });

  }

  openConfirm():void{
    this._dialogService.openConfirm({
      message: 'Você realmente deseja remover esta categoria?',
      cancelButton: 'Cancelar',
      acceptButton: 'Sim'
    }).afterClosed().subscribe((accept: boolean)=>{
      if (accept) {
        this.openAlert("deletada com sucesso!");
      } else{
        
      }
    })
  }


  }
    


