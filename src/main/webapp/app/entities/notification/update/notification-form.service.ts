import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { INotification, NewNotification } from '../notification.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts INotification for edit and NewNotificationFormGroupInput for create.
 */
type NotificationFormGroupInput = INotification | PartialWithRequiredKeyOf<NewNotification>;

type NotificationFormDefaults = Pick<NewNotification, 'id'>;

type NotificationFormGroupContent = {
  id: FormControl<INotification['id'] | NewNotification['id']>;
  status: FormControl<INotification['status']>;
  content: FormControl<INotification['content']>;
  cost: FormControl<INotification['cost']>;
  email: FormControl<INotification['email']>;
  msisdn: FormControl<INotification['msisdn']>;
  notificationType: FormControl<INotification['notificationType']>;
  configs: FormControl<INotification['configs']>;
  createdOn: FormControl<INotification['createdOn']>;
  createdBy: FormControl<INotification['createdBy']>;
  lastUpdatedOn: FormControl<INotification['lastUpdatedOn']>;
  lastUpdatedBy: FormControl<INotification['lastUpdatedBy']>;
  association: FormControl<INotification['association']>;
};

export type NotificationFormGroup = FormGroup<NotificationFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class NotificationFormService {
  createNotificationFormGroup(notification: NotificationFormGroupInput = { id: null }): NotificationFormGroup {
    const notificationRawValue = {
      ...this.getFormDefaults(),
      ...notification,
    };
    return new FormGroup<NotificationFormGroupContent>({
      id: new FormControl(
        { value: notificationRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      status: new FormControl(notificationRawValue.status),
      content: new FormControl(notificationRawValue.content),
      cost: new FormControl(notificationRawValue.cost),
      email: new FormControl(notificationRawValue.email),
      msisdn: new FormControl(notificationRawValue.msisdn),
      notificationType: new FormControl(notificationRawValue.notificationType),
      configs: new FormControl(notificationRawValue.configs),
      createdOn: new FormControl(notificationRawValue.createdOn),
      createdBy: new FormControl(notificationRawValue.createdBy),
      lastUpdatedOn: new FormControl(notificationRawValue.lastUpdatedOn),
      lastUpdatedBy: new FormControl(notificationRawValue.lastUpdatedBy),
      association: new FormControl(notificationRawValue.association),
    });
  }

  getNotification(form: NotificationFormGroup): INotification | NewNotification {
    return form.getRawValue() as INotification | NewNotification;
  }

  resetForm(form: NotificationFormGroup, notification: NotificationFormGroupInput): void {
    const notificationRawValue = { ...this.getFormDefaults(), ...notification };
    form.reset(
      {
        ...notificationRawValue,
        id: { value: notificationRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): NotificationFormDefaults {
    return {
      id: null,
    };
  }
}
