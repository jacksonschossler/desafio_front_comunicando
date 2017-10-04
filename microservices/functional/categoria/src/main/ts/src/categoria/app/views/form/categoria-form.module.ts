import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms'
import { CategoriaFormComponent } from './categoria-form.component';


@NgModule({
    imports: [
                CommonModule, 
                ReactiveFormsModule
            ],
    declarations: [
        //CategoriaFormComponent
    ]
})

export class CategoriaFormModule {}