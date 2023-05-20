import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { AssocMemberFormService, AssocMemberFormGroup } from './assoc-member-form.service';
import { IAssocMember } from '../assoc-member.model';
import { AssocMemberService } from '../service/assoc-member.service';
import { IPledge } from 'app/entities/pledge/pledge.model';
import { PledgeService } from 'app/entities/pledge/service/pledge.service';
import { IAssociation } from 'app/entities/association/association.model';
import { AssociationService } from 'app/entities/association/service/association.service';
import { Role } from 'app/entities/enumerations/role.model';

@Component({
  selector: 'jhi-assoc-member-update',
  templateUrl: './assoc-member-update.component.html',
})
export class AssocMemberUpdateComponent implements OnInit {
  isSaving = false;
  assocMember: IAssocMember | null = null;
  roleValues = Object.keys(Role);

  pledgesSharedCollection: IPledge[] = [];
  associationsSharedCollection: IAssociation[] = [];

  editForm: AssocMemberFormGroup = this.assocMemberFormService.createAssocMemberFormGroup();

  constructor(
    protected assocMemberService: AssocMemberService,
    protected assocMemberFormService: AssocMemberFormService,
    protected pledgeService: PledgeService,
    protected associationService: AssociationService,
    protected activatedRoute: ActivatedRoute
  ) {}

  comparePledge = (o1: IPledge | null, o2: IPledge | null): boolean => this.pledgeService.comparePledge(o1, o2);

  compareAssociation = (o1: IAssociation | null, o2: IAssociation | null): boolean => this.associationService.compareAssociation(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ assocMember }) => {
      this.assocMember = assocMember;
      if (assocMember) {
        this.updateForm(assocMember);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const assocMember = this.assocMemberFormService.getAssocMember(this.editForm);
    if (assocMember.id !== null) {
      this.subscribeToSaveResponse(this.assocMemberService.update(assocMember));
    } else {
      this.subscribeToSaveResponse(this.assocMemberService.create(assocMember));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAssocMember>>): void {
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

  protected updateForm(assocMember: IAssocMember): void {
    this.assocMember = assocMember;
    this.assocMemberFormService.resetForm(this.editForm, assocMember);

    this.pledgesSharedCollection = this.pledgeService.addPledgeToCollectionIfMissing<IPledge>(
      this.pledgesSharedCollection,
      assocMember.pledge
    );
    this.associationsSharedCollection = this.associationService.addAssociationToCollectionIfMissing<IAssociation>(
      this.associationsSharedCollection,
      ...(assocMember.associations ?? [])
    );
  }

  protected loadRelationshipsOptions(): void {
    this.pledgeService
      .query()
      .pipe(map((res: HttpResponse<IPledge[]>) => res.body ?? []))
      .pipe(map((pledges: IPledge[]) => this.pledgeService.addPledgeToCollectionIfMissing<IPledge>(pledges, this.assocMember?.pledge)))
      .subscribe((pledges: IPledge[]) => (this.pledgesSharedCollection = pledges));

    this.associationService
      .query()
      .pipe(map((res: HttpResponse<IAssociation[]>) => res.body ?? []))
      .pipe(
        map((associations: IAssociation[]) =>
          this.associationService.addAssociationToCollectionIfMissing<IAssociation>(associations, ...(this.assocMember?.associations ?? []))
        )
      )
      .subscribe((associations: IAssociation[]) => (this.associationsSharedCollection = associations));
  }
}
