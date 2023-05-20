import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { AssociationFormService, AssociationFormGroup } from './association-form.service';
import { IAssociation } from '../association.model';
import { AssociationService } from '../service/association.service';
import { AssociationType } from 'app/entities/enumerations/association-type.model';

@Component({
  selector: 'jhi-association-update',
  templateUrl: './association-update.component.html',
})
export class AssociationUpdateComponent implements OnInit {
  isSaving = false;
  association: IAssociation | null = null;
  associationTypeValues = Object.keys(AssociationType);

  editForm: AssociationFormGroup = this.associationFormService.createAssociationFormGroup();

  constructor(
    protected associationService: AssociationService,
    protected associationFormService: AssociationFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ association }) => {
      this.association = association;
      if (association) {
        this.updateForm(association);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const association = this.associationFormService.getAssociation(this.editForm);
    if (association.id !== null) {
      this.subscribeToSaveResponse(this.associationService.update(association));
    } else {
      this.subscribeToSaveResponse(this.associationService.create(association));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAssociation>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(association: IAssociation): void {
    this.association = association;
    this.associationFormService.resetForm(this.editForm, association);
  }
}
