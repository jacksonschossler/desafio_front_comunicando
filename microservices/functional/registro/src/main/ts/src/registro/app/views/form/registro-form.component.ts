import { Broker } from 'eits-ng2';
import { Http } from '@angular/http';
import { Component, OnInit, Input, ViewContainerRef } from '@angular/core';
import { NgModel, FormControl, FormGroup, FormBuilder, Validators } from '@angular/forms'
import { MdDialog } from '@angular/material';
import { TdDialogService, TdMediaService } from '@covalent/core';
import { Router, ActivatedRoute } from '@angular/router';


@Component({
    selector: 'registro-form',
    templateUrl: './registro-form.component.html'
})
export class RegistroFormComponent implements OnInit{

    formulario: FormGroup;
    

    @Input()
    registro: any ={};
    registros: any[] = [];

    categorias: any[] = [];
    categoria: any;

    myControl : FormControl = new FormControl();

    tipos = [
        { value: 'DESPESA', viewValue: 'DESPESA'},
        { value: 'RECEITA', viewValue: 'RECEITA'}
    ];



    constructor(private _dialogService: TdDialogService,
        private _viewContainerRef: ViewContainerRef,
        public router: Router, public formBuilder: FormBuilder, 
        public activatedRoute: ActivatedRoute)
        {

            this.formulario = formBuilder.group({
                tipoForm: ['', Validators.required],
                categoriaForm: ['', Validators.required],
                valorForm:['', Validators.required],
                dataForm:['', Validators.required],
                descricaoForm:['']

        });
        }
    

    ngOnInit(): void {
        this.listCategoriaByFiltersDesativada(null,null);
        let registroId: number = this.activatedRoute.snapshot.params['id'];
        if(registroId){
          this.findRegistroById(registroId);
        };
    }
    
    public findRegistroById = function(registroId){
      Broker.of("registroService").promise("findRegistroById", registroId)
      .then((result)=> {
        this.registro = result;
      })

    }

    public listCategoriaByFiltersDesativada = function (nome, pageable)
    {
        Broker.of("registroService").promise("listCategoriaByFiltersDesativada", nome, pageable)
        .then((result)=> {
            this.categorias = result.content;
        })
        .catch((exception)=> {
          this.openAlert(exception.message);
        })
    }


    public updateRegistro(registro): void{
      if (this.formulario.valid){
        Broker.of("registroService").promise("updateRegistro", registro)
        .then((registro)=>{
          this.openAlert("Editado com sucesso!");
          this.router.navigate(["./registro/list"]);
        })
        .catch((exception)=>{
          this.openAlert(exception.message);
        })
      }
    }

    public insertRegistro(registro): void{
      if (this.formulario.valid){
        Broker.of("registroService").promise("insertRegistro", registro)
        .then((registro)=>{
          this.openAlert("Salvo com sucesso!");
          this.router.navigate(["./registro/list"]);
        })
        .catch((exception)=>{
          this.openAlert(exception.message);
        })
      }
    }

    


    openAlert(mensagem: String): void{
      this._dialogService.openAlert({
          title: "Registro",    
          message: (""+ mensagem)
      
      });

  }

    displayCategoria(categoria): String {
        return categoria ? categoria.nome : "";
    }

    
}