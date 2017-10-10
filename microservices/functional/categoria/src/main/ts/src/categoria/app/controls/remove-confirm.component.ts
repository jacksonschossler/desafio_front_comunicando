import { Broker } from 'eits-ng2';
import { Component, Inject, ViewContainerRef } from "@angular/core";
import { CategoriaViewComponent } from './../views/categoria/categoria-view.component';
import { MdDialog, MdDialogRef, MD_DIALOG_DATA} from '@angular/material';
import { FormControl, Validators } from '@angular/forms';
import { TdDialogService } from '@covalent/core';


@Component({
    selector: 'remove-confirm',
    templateUrl: './remove-confirm.component.html'
})
export class RemoveConfirmComponent{
    

    comentarioFormControl = new FormControl('',[Validators.required])

    constructor( public dialogRef: MdDialogRef<CategoriaViewComponent>, 
                @Inject(MD_DIALOG_DATA) public data: any,private _dialogService: TdDialogService,
                private _viewContainerRef: ViewContainerRef )
    {

    }   
    onNoClick():void{
        this.dialogRef.close();
    }

    onYesClick():void{
        Broker.of("categoriaService").promise("removeCategoria", this.data.id)
        .then((result) => {
            this.dialogRef.close(this.data.id);
            this.openAlert("deletada com sucesso!");
        })
        .catch((exception) =>{
            this.openAlert(exception.message);
            this.dialogRef.close();
        })
    }

    openAlert(mensagem: String): void{
            this._dialogService.openAlert({
            title: "Categoria",    
            message: (""+ mensagem)
        
        });

    }




}