import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IAssocMember, NewAssocMember } from '../assoc-member.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAssocMember for edit and NewAssocMemberFormGroupInput for create.
 */
type AssocMemberFormGroupInput = IAssocMember | PartialWithRequiredKeyOf<NewAssocMember>;

type AssocMemberFormDefaults = Pick<NewAssocMember, 'id' | 'associations'>;

type AssocMemberFormGroupContent = {
  id: FormControl<IAssocMember['id'] | NewAssocMember['id']>;
  name: FormControl<IAssocMember['name']>;
  email: FormControl<IAssocMember['email']>;
  phone: FormControl<IAssocMember['phone']>;
  year: FormControl<IAssocMember['year']>;
  regNo: FormControl<IAssocMember['regNo']>;
  role: FormControl<IAssocMember['role']>;
  configs: FormControl<IAssocMember['configs']>;
  createdBy: FormControl<IAssocMember['createdBy']>;
  createdOn: FormControl<IAssocMember['createdOn']>;
  lastUpdatedBy: FormControl<IAssocMember['lastUpdatedBy']>;
  lastUpdatedOn: FormControl<IAssocMember['lastUpdatedOn']>;
  pledge: FormControl<IAssocMember['pledge']>;
  associations: FormControl<IAssocMember['associations']>;
};

export type AssocMemberFormGroup = FormGroup<AssocMemberFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AssocMemberFormService {
  createAssocMemberFormGroup(assocMember: AssocMemberFormGroupInput = { id: null }): AssocMemberFormGroup {
    const assocMemberRawValue = {
      ...this.getFormDefaults(),
      ...assocMember,
    };
    return new FormGroup<AssocMemberFormGroupContent>({
      id: new FormControl(
        { value: assocMemberRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      name: new FormControl(assocMemberRawValue.name),
      email: new FormControl(assocMemberRawValue.email),
      phone: new FormControl(assocMemberRawValue.phone),
      year: new FormControl(assocMemberRawValue.year),
      regNo: new FormControl(assocMemberRawValue.regNo),
      role: new FormControl(assocMemberRawValue.role),
      configs: new FormControl(assocMemberRawValue.configs),
      createdBy: new FormControl(assocMemberRawValue.createdBy),
      createdOn: new FormControl(assocMemberRawValue.createdOn),
      lastUpdatedBy: new FormControl(assocMemberRawValue.lastUpdatedBy),
      lastUpdatedOn: new FormControl(assocMemberRawValue.lastUpdatedOn),
      pledge: new FormControl(assocMemberRawValue.pledge),
      associations: new FormControl(assocMemberRawValue.associations ?? []),
    });
  }

  getAssocMember(form: AssocMemberFormGroup): IAssocMember | NewAssocMember {
    return form.getRawValue() as IAssocMember | NewAssocMember;
  }

  resetForm(form: AssocMemberFormGroup, assocMember: AssocMemberFormGroupInput): void {
    const assocMemberRawValue = { ...this.getFormDefaults(), ...assocMember };
    form.reset(
      {
        ...assocMemberRawValue,
        id: { value: assocMemberRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): AssocMemberFormDefaults {
    return {
      id: null,
      associations: [],
    };
  }
}
