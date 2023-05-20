import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { NotificationFormService } from './notification-form.service';
import { NotificationService } from '../service/notification.service';
import { INotification } from '../notification.model';
import { IAssociation } from 'app/entities/association/association.model';
import { AssociationService } from 'app/entities/association/service/association.service';

import { NotificationUpdateComponent } from './notification-update.component';

describe('Notification Management Update Component', () => {
  let comp: NotificationUpdateComponent;
  let fixture: ComponentFixture<NotificationUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let notificationFormService: NotificationFormService;
  let notificationService: NotificationService;
  let associationService: AssociationService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [NotificationUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(NotificationUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(NotificationUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    notificationFormService = TestBed.inject(NotificationFormService);
    notificationService = TestBed.inject(NotificationService);
    associationService = TestBed.inject(AssociationService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Association query and add missing value', () => {
      const notification: INotification = { id: 456 };
      const association: IAssociation = { id: 67838 };
      notification.association = association;

      const associationCollection: IAssociation[] = [{ id: 39056 }];
      jest.spyOn(associationService, 'query').mockReturnValue(of(new HttpResponse({ body: associationCollection })));
      const additionalAssociations = [association];
      const expectedCollection: IAssociation[] = [...additionalAssociations, ...associationCollection];
      jest.spyOn(associationService, 'addAssociationToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ notification });
      comp.ngOnInit();

      expect(associationService.query).toHaveBeenCalled();
      expect(associationService.addAssociationToCollectionIfMissing).toHaveBeenCalledWith(
        associationCollection,
        ...additionalAssociations.map(expect.objectContaining)
      );
      expect(comp.associationsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const notification: INotification = { id: 456 };
      const association: IAssociation = { id: 14815 };
      notification.association = association;

      activatedRoute.data = of({ notification });
      comp.ngOnInit();

      expect(comp.associationsSharedCollection).toContain(association);
      expect(comp.notification).toEqual(notification);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<INotification>>();
      const notification = { id: 123 };
      jest.spyOn(notificationFormService, 'getNotification').mockReturnValue(notification);
      jest.spyOn(notificationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ notification });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: notification }));
      saveSubject.complete();

      // THEN
      expect(notificationFormService.getNotification).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(notificationService.update).toHaveBeenCalledWith(expect.objectContaining(notification));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<INotification>>();
      const notification = { id: 123 };
      jest.spyOn(notificationFormService, 'getNotification').mockReturnValue({ id: null });
      jest.spyOn(notificationService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ notification: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: notification }));
      saveSubject.complete();

      // THEN
      expect(notificationFormService.getNotification).toHaveBeenCalled();
      expect(notificationService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<INotification>>();
      const notification = { id: 123 };
      jest.spyOn(notificationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ notification });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(notificationService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareAssociation', () => {
      it('Should forward to associationService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(associationService, 'compareAssociation');
        comp.compareAssociation(entity, entity2);
        expect(associationService.compareAssociation).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
