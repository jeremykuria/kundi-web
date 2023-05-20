import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IPayment, NewPayment } from '../payment.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPayment for edit and NewPaymentFormGroupInput for create.
 */
type PaymentFormGroupInput = IPayment | PartialWithRequiredKeyOf<NewPayment>;

type PaymentFormDefaults = Pick<NewPayment, 'id'>;

type PaymentFormGroupContent = {
  id: FormControl<IPayment['id'] | NewPayment['id']>;
  productId: FormControl<IPayment['productId']>;
  code: FormControl<IPayment['code']>;
  subscriberId: FormControl<IPayment['subscriberId']>;
  receiptId: FormControl<IPayment['receiptId']>;
  orderId: FormControl<IPayment['orderId']>;
  phone: FormControl<IPayment['phone']>;
  amount: FormControl<IPayment['amount']>;
  currency: FormControl<IPayment['currency']>;
  paybill: FormControl<IPayment['paybill']>;
  trxId: FormControl<IPayment['trxId']>;
  trxEndTime: FormControl<IPayment['trxEndTime']>;
  trxStartTime: FormControl<IPayment['trxStartTime']>;
  status: FormControl<IPayment['status']>;
  statusReason: FormControl<IPayment['statusReason']>;
  configs: FormControl<IPayment['configs']>;
  createdBy: FormControl<IPayment['createdBy']>;
  lastUpdatedOn: FormControl<IPayment['lastUpdatedOn']>;
  lastUpdtedBy: FormControl<IPayment['lastUpdtedBy']>;
  project: FormControl<IPayment['project']>;
};

export type PaymentFormGroup = FormGroup<PaymentFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PaymentFormService {
  createPaymentFormGroup(payment: PaymentFormGroupInput = { id: null }): PaymentFormGroup {
    const paymentRawValue = {
      ...this.getFormDefaults(),
      ...payment,
    };
    return new FormGroup<PaymentFormGroupContent>({
      id: new FormControl(
        { value: paymentRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      productId: new FormControl(paymentRawValue.productId),
      code: new FormControl(paymentRawValue.code),
      subscriberId: new FormControl(paymentRawValue.subscriberId),
      receiptId: new FormControl(paymentRawValue.receiptId),
      orderId: new FormControl(paymentRawValue.orderId),
      phone: new FormControl(paymentRawValue.phone),
      amount: new FormControl(paymentRawValue.amount),
      currency: new FormControl(paymentRawValue.currency),
      paybill: new FormControl(paymentRawValue.paybill),
      trxId: new FormControl(paymentRawValue.trxId),
      trxEndTime: new FormControl(paymentRawValue.trxEndTime),
      trxStartTime: new FormControl(paymentRawValue.trxStartTime),
      status: new FormControl(paymentRawValue.status),
      statusReason: new FormControl(paymentRawValue.statusReason),
      configs: new FormControl(paymentRawValue.configs),
      createdBy: new FormControl(paymentRawValue.createdBy),
      lastUpdatedOn: new FormControl(paymentRawValue.lastUpdatedOn),
      lastUpdtedBy: new FormControl(paymentRawValue.lastUpdtedBy),
      project: new FormControl(paymentRawValue.project),
    });
  }

  getPayment(form: PaymentFormGroup): IPayment | NewPayment {
    return form.getRawValue() as IPayment | NewPayment;
  }

  resetForm(form: PaymentFormGroup, payment: PaymentFormGroupInput): void {
    const paymentRawValue = { ...this.getFormDefaults(), ...payment };
    form.reset(
      {
        ...paymentRawValue,
        id: { value: paymentRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): PaymentFormDefaults {
    return {
      id: null,
    };
  }
}
