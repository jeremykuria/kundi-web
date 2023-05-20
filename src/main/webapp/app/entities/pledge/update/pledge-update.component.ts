import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { PledgeFormService, PledgeFormGroup } from './pledge-form.service';
import { IPledge } from '../pledge.model';
import { PledgeService } from '../service/pledge.service';
import { IProject } from 'app/entities/project/project.model';
import { ProjectService } from 'app/entities/project/service/project.service';
import { Status } from 'app/entities/enumerations/status.model';
import { ReminderCycle } from 'app/entities/enumerations/reminder-cycle.model';

@Component({
  selector: 'jhi-pledge-update',
  templateUrl: './pledge-update.component.html',
})
export class PledgeUpdateComponent implements OnInit {
  isSaving = false;
  pledge: IPledge | null = null;
  statusValues = Object.keys(Status);
  reminderCycleValues = Object.keys(ReminderCycle);

  projectsSharedCollection: IProject[] = [];

  editForm: PledgeFormGroup = this.pledgeFormService.createPledgeFormGroup();

  constructor(
    protected pledgeService: PledgeService,
    protected pledgeFormService: PledgeFormService,
    protected projectService: ProjectService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareProject = (o1: IProject | null, o2: IProject | null): boolean => this.projectService.compareProject(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pledge }) => {
      this.pledge = pledge;
      if (pledge) {
        this.updateForm(pledge);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const pledge = this.pledgeFormService.getPledge(this.editForm);
    if (pledge.id !== null) {
      this.subscribeToSaveResponse(this.pledgeService.update(pledge));
    } else {
      this.subscribeToSaveResponse(this.pledgeService.create(pledge));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPledge>>): void {
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

  protected updateForm(pledge: IPledge): void {
    this.pledge = pledge;
    this.pledgeFormService.resetForm(this.editForm, pledge);

    this.projectsSharedCollection = this.projectService.addProjectToCollectionIfMissing<IProject>(
      this.projectsSharedCollection,
      pledge.project
    );
  }

  protected loadRelationshipsOptions(): void {
    this.projectService
      .query()
      .pipe(map((res: HttpResponse<IProject[]>) => res.body ?? []))
      .pipe(map((projects: IProject[]) => this.projectService.addProjectToCollectionIfMissing<IProject>(projects, this.pledge?.project)))
      .subscribe((projects: IProject[]) => (this.projectsSharedCollection = projects));
  }
}
