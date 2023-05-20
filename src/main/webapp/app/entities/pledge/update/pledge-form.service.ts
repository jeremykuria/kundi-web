import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IPledge, NewPledge } from '../pledge.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPledge for edit and NewPledgeFormGroupInput for create.
 */
type PledgeFormGroupInput = IPledge | PartialWithRequiredKeyOf<NewPledge>;

type PledgeFormDefaults = Pick<NewPledge, 'id'>;

type PledgeFormGroupContent = {
  id: FormControl<IPledge['id'] | NewPledge['id']>;
  targetAmount: FormControl<IPledge['targetAmount']>;
  paidAmount: FormControl<IPledge['paidAmount']>;
  status: FormControl<IPledge['status']>;
  noOfReminders: FormControl<IPledge['noOfReminders']>;
  reminderCycle: FormControl<IPledge['reminderCycle']>;
  configs: FormControl<IPledge['configs']>;
  createdOn: FormControl<IPledge['createdOn']>;
  createdBy: FormControl<IPledge['createdBy']>;
  lastUpdatedOn: FormControl<IPledge['lastUpdatedOn']>;
  lastUpdatedBy: FormControl<IPledge['lastUpdatedBy']>;
  project: FormControl<IPledge['project']>;
};

export type PledgeFormGroup = FormGroup<PledgeFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PledgeFormService {
  createPledgeFormGroup(pledge: PledgeFormGroupInput = { id: null }): PledgeFormGroup {
    const pledgeRawValue = {
      ...this.getFormDefaults(),
      ...pledge,
    };
    return new FormGroup<PledgeFormGroupContent>({
      id: new FormControl(
        { value: pledgeRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      targetAmount: new FormControl(pledgeRawValue.targetAmount),
      paidAmount: new FormControl(pledgeRawValue.paidAmount),
      status: new FormControl(pledgeRawValue.status),
      noOfReminders: new FormControl(pledgeRawValue.noOfReminders),
      reminderCycle: new FormControl(pledgeRawValue.reminderCycle),
      configs: new FormControl(pledgeRawValue.configs),
      createdOn: new FormControl(pledgeRawValue.createdOn),
      createdBy: new FormControl(pledgeRawValue.createdBy),
      lastUpdatedOn: new FormControl(pledgeRawValue.lastUpdatedOn),
      lastUpdatedBy: new FormControl(pledgeRawValue.lastUpdatedBy),
      project: new FormControl(pledgeRawValue.project),
    });
  }

  getPledge(form: PledgeFormGroup): IPledge | NewPledge {
    return form.getRawValue() as IPledge | NewPledge;
  }

  resetForm(form: PledgeFormGroup, pledge: PledgeFormGroupInput): void {
    const pledgeRawValue = { ...this.getFormDefaults(), ...pledge };
    form.reset(
      {
        ...pledgeRawValue,
        id: { value: pledgeRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): PledgeFormDefaults {
    return {
      id: null,
    };
  }
}
