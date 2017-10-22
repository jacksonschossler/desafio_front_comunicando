import { Broker } from 'eits-ng2';
import { Http } from '@angular/http';
import { Component, OnInit,ViewContainerRef  } from '@angular/core';
import { TdMediaService,TdDialogService   } from '@covalent/core';
import { FormGroup, FormControl, FormBuilder, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { MdSnackBar } from '@angular/material';


@Component({
    selector: 'categoria-form',
    templateUrl: './categoria-form.component.html'
    //styleUrls: ['./categoria-form.component.css']
})
export class CategoriaFormComponent implements OnInit {

    formulario: FormGroup;

    myControl : FormControl = new FormControl();

    tipos = [
        { value: 'DESPESA', viewValue: 'DESPESA'},
        { value: 'RECEITA', viewValue: 'RECEITA'}
    ];

    categoria:  any = {};

    

    constructor(private _dialogService: TdDialogService,
        private _viewContainerRef: ViewContainerRef,
        public router: Router, public formBuilder: FormBuilder,
        public activatedRoute: ActivatedRoute, public snackBar: MdSnackBar)
        {
            this.formulario = formBuilder.group({
                tipoForm: ['', Validators.required],
                nomeForm:['', Validators.required],
                descricaoForm:['']
            });


         }

    ngOnInit(){

        let categoriaId: number = this.activatedRoute.snapshot.params['id'];
        if(categoriaId){
            this.findCategoriaById(categoriaId);
        };
        //this.formulario = new FormGroup({
        //    tipo: new FormControl(null),
        //    nome: new FormControl(null),
        //    descricao: new FormControl(null)
       // });
    }


    public insertCategoria(categoria): void{
        if (this.formulario.valid){
            Broker.of("categoriaService").promise("insertCategoria", categoria)
            .then((categoria) => {
                //this.openAlert("salva com sucesso!");
                this.openSnackBar("Categoria salva com sucesso!");
                this.router.navigate(["./categoria/list"]);
            })
            .catch((exception) => {
                this.openAlert(exception.message)
            })
        }
    }

    public updateCategoria(categoria): void{
        if (this.formulario.valid){
            Broker.of("categoriaService").promise("updateCategoria", categoria)
            .then((categoria) =>{
                //this.openAlert("editada com sucesso!");
                this.openSnackBar("Categoria editada com sucesso!");
                this.router.navigate(["./categoria/list"]);

            })
        }
    }

    public updateCategoriaToDesativada(): void{
        if (this.formulario.valid){
            Broker.of("categoriaService").promise("updateCategoriaToDesativada", )
        }

    }

    public findCategoriaById = function (categoriaId){
        Broker.of("categoriaService").promise("findCategoriaById", categoriaId)
        .then((result) => {
            this.categoria = result;
        })
    }
    

    openAlert(mensagem: String): void{
        this._dialogService.openAlert({
            title: "Categoria",    
            message: (""+ mensagem)
        
        });
    }
    openSnackBar(messagem: string) {
      this.snackBar.open(messagem, "Ok", {
          duration: 5000,
      });
  }


    displayTipo(tipo): String {
        return tipo ? tipo.viewValue : "";
    }

}