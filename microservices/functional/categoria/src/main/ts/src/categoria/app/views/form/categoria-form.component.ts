import { Broker } from 'eits-ng2';
import { Http } from '@angular/http';
import { Component, OnInit, HostBinding, AfterViewInit, ChangeDetectionStrategy, ChangeDetectorRef } from '@angular/core';
import { TdMediaService } from '@covalent/core';
import { FormGroup, FormControl, Validator, FormBuilder } from "@angular/forms";


@Component({
    selector: 'categoria-form',
    templateUrl: './categoria-form.component.html',
    styleUrls: ['./categoria-form.component.css']
})
export class CategoriaFormComponent implements OnInit {

    formulario: FormGroup;

    myControl : FormControl = new FormControl();

    tipos = [
        { value: 'DESPESA', viewValue: 'DESPESA'},
        { value: 'RECEITA', viewValue: 'RECEITA'}
    ];


    constructor() { }

    ngOnInit(){

        this.formulario = new FormGroup({
            tipo: new FormControl(null),
            nome: new FormControl(null),
            descricao: new FormControl(null)
        });
    }

    displayTipo(tipo): String {
        return tipo ? tipo.viewValue : "";
    }

}