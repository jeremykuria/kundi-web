import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IProject, NewProject } from '../project.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IProject for edit and NewProjectFormGroupInput for create.
 */
type ProjectFormGroupInput = IProject | PartialWithRequiredKeyOf<NewProject>;

type ProjectFormDefaults = Pick<NewProject, 'id'>;

type ProjectFormGroupContent = {
  id: FormControl<IProject['id'] | NewProject['id']>;
  name: FormControl<IProject['name']>;
  targetAmount: FormControl<IProject['targetAmount']>;
  startDate: FormControl<IProject['startDate']>;
  endDate: FormControl<IProject['endDate']>;
  noOfPledgeReminder: FormControl<IProject['noOfPledgeReminder']>;
  contributedAmount: FormControl<IProject['contributedAmount']>;
  paybill: FormControl<IProject['paybill']>;
  configs: FormControl<IProject['configs']>;
  createdBy: FormControl<IProject['createdBy']>;
  createdOn: FormControl<IProject['createdOn']>;
  lastUpdatedOn: FormControl<IProject['lastUpdatedOn']>;
  lastUpdatedBy: FormControl<IProject['lastUpdatedBy']>;
  association: FormControl<IProject['association']>;
};

export type ProjectFormGroup = FormGroup<ProjectFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ProjectFormService {
  createProjectFormGroup(project: ProjectFormGroupInput = { id: null }): ProjectFormGroup {
    const projectRawValue = {
      ...this.getFormDefaults(),
      ...project,
    };
    return new FormGroup<ProjectFormGroupContent>({
      id: new FormControl(
        { value: projectRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      name: new FormControl(projectRawValue.name),
      targetAmount: new FormControl(projectRawValue.targetAmount),
      startDate: new FormControl(projectRawValue.startDate),
      endDate: new FormControl(projectRawValue.endDate),
      noOfPledgeReminder: new FormControl(projectRawValue.noOfPledgeReminder),
      contributedAmount: new FormControl(projectRawValue.contributedAmount),
      paybill: new FormControl(projectRawValue.paybill),
      configs: new FormControl(projectRawValue.configs),
      createdBy: new FormControl(projectRawValue.createdBy),
      createdOn: new FormControl(projectRawValue.createdOn),
      lastUpdatedOn: new FormControl(projectRawValue.lastUpdatedOn),
      lastUpdatedBy: new FormControl(projectRawValue.lastUpdatedBy),
      association: new FormControl(projectRawValue.association),
    });
  }

  getProject(form: ProjectFormGroup): IProject | NewProject {
    return form.getRawValue() as IProject | NewProject;
  }

  resetForm(form: ProjectFormGroup, project: ProjectFormGroupInput): void {
    const projectRawValue = { ...this.getFormDefaults(), ...project };
    form.reset(
      {
        ...projectRawValue,
        id: { value: projectRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ProjectFormDefaults {
    return {
      id: null,
    };
  }
}
