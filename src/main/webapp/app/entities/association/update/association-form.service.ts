import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IAssociation, NewAssociation } from '../association.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAssociation for edit and NewAssociationFormGroupInput for create.
 */
type AssociationFormGroupInput = IAssociation | PartialWithRequiredKeyOf<NewAssociation>;

type AssociationFormDefaults = Pick<NewAssociation, 'id' | 'members'>;

type AssociationFormGroupContent = {
  id: FormControl<IAssociation['id'] | NewAssociation['id']>;
  name: FormControl<IAssociation['name']>;
  brief: FormControl<IAssociation['brief']>;
  description: FormControl<IAssociation['description']>;
  phoneNumber: FormControl<IAssociation['phoneNumber']>;
  contentStatus: FormControl<IAssociation['contentStatus']>;
  paybill: FormControl<IAssociation['paybill']>;
  associationType: FormControl<IAssociation['associationType']>;
  configs: FormControl<IAssociation['configs']>;
  createdBy: FormControl<IAssociation['createdBy']>;
  createdOn: FormControl<IAssociation['createdOn']>;
  lastUpdatedOn: FormControl<IAssociation['lastUpdatedOn']>;
  lastUpdatedBy: FormControl<IAssociation['lastUpdatedBy']>;
  members: FormControl<IAssociation['members']>;
};

export type AssociationFormGroup = FormGroup<AssociationFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AssociationFormService {
  createAssociationFormGroup(association: AssociationFormGroupInput = { id: null }): AssociationFormGroup {
    const associationRawValue = {
      ...this.getFormDefaults(),
      ...association,
    };
    return new FormGroup<AssociationFormGroupContent>({
      id: new FormControl(
        { value: associationRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      name: new FormControl(associationRawValue.name),
      brief: new FormControl(associationRawValue.brief),
      description: new FormControl(associationRawValue.description),
      phoneNumber: new FormControl(associationRawValue.phoneNumber),
      contentStatus: new FormControl(associationRawValue.contentStatus),
      paybill: new FormControl(associationRawValue.paybill),
      associationType: new FormControl(associationRawValue.associationType),
      configs: new FormControl(associationRawValue.configs),
      createdBy: new FormControl(associationRawValue.createdBy),
      createdOn: new FormControl(associationRawValue.createdOn),
      lastUpdatedOn: new FormControl(associationRawValue.lastUpdatedOn),
      lastUpdatedBy: new FormControl(associationRawValue.lastUpdatedBy),
      members: new FormControl(associationRawValue.members ?? []),
    });
  }

  getAssociation(form: AssociationFormGroup): IAssociation | NewAssociation {
    return form.getRawValue() as IAssociation | NewAssociation;
  }

  resetForm(form: AssociationFormGroup, association: AssociationFormGroupInput): void {
    const associationRawValue = { ...this.getFormDefaults(), ...association };
    form.reset(
      {
        ...associationRawValue,
        id: { value: associationRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): AssociationFormDefaults {
    return {
      id: null,
      members: [],
    };
  }
}
