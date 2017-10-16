import { Broker } from 'eits-ng2';
import { Component, OnInit, Input, ViewContainerRef } from '@angular/core';
import { NgModel } from '@angular/forms'
import { MdDialog } from '@angular/material';
import { TdDialogService } from '@covalent/core';


@Component({
    selector: 'registro-chart',
    templateUrl: './registro-chart.component.html'
})
export class RegistroChartComponent implements OnInit{



  @Input()
  registro: any = {};

  filtro: any = {};

  categorias: any[] = [];
  categoria: any;
  registros: any[] = [];
  valorDespesa : number = 0;
  valorReceita : number = 0;
  public mostra: any;
  public registrosNomeReceita:any[] = [];
  public registrosValorReceita:number[] = [];
  public registrosNomeDespesa:any[] = [];
  public registrosValorDespesa:number[] = [];
  public registrosNomeTipo:any[] = ['DESPESA', 'RECEITA'];
  public registrosValorTipo:number[] = [];

  public pieChartType:string = 'pie';
                        
  public meses = [
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
    private _viewContainerRef: ViewContainerRef) {


  }
  
  ngOnInit(): void {
    //this.listRegistroById(null, null, null, null);
  }

  // events
  public chartClicked(e:any):void {
    console.log(e);
  }
 
  public chartHovered(e:any):void {
    console.log(e);
  }

  public populaArrayTipo (){
    // this.registrosNome.shift();
    // this.registrosValor.shift();

    for (let i in this.registros){
      if (this.registros[i].tipo=="DESPESA"){
        //this.registrosNome.push(this.registros[i].categoria.nome);
        //this.registrosValorTipo.push(this.registros[i].valor);
        this.valorDespesa = this.valorDespesa + this.registros[i].valor;
      }else {
        this.valorReceita = this.valorReceita + this.registros[i].valor;
      }
    }
    this.registrosValorTipo[0] = this.valorDespesa; 
    this.registrosValorTipo[1] = this.valorReceita; 

  }

  public populaArray (){
    // this.registrosNome.shift();
    // this.registrosValor.shift();
    for (let i in this.registros){
      if (this.registros[i].tipo=="RECEITA"){
        this.registrosNomeReceita.push(this.registros[i].categoria.nome);
        this.registrosValorReceita.push(this.registros[i].valor);
      }else{
        this.registrosNomeDespesa.push(this.registros[i].categoria.nome);
        this.registrosValorDespesa.push(this.registros[i].valor);
      }
      
    }

  }
  
  public listRegistroById = function (mes, ano, categoria, pageable) {
    Broker.of("registroService").promise("listRegistroById", mes, ano, categoria, pageable)
      .then((result) => {
        this.registros = result.content;
        this.populaArray();
        this.populaArrayTipo();
      })
      .catch((exception) => {
        console.log(exception.message);
      })
  }
    
  public filtrar = function () {
    this.listRegistroById(this.filtro.mes, this.filtro.ano, null, null);
    this.mostra = 1;
  }

    openAlert(mensagem: String): void{
      this._dialogService.openAlert({
          title: "Registro",    
          message: (""+ mensagem)
      
      });
  
  }

}