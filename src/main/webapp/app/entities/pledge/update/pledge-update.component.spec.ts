import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PledgeFormService } from './pledge-form.service';
import { PledgeService } from '../service/pledge.service';
import { IPledge } from '../pledge.model';
import { IProject } from 'app/entities/project/project.model';
import { ProjectService } from 'app/entities/project/service/project.service';

import { PledgeUpdateComponent } from './pledge-update.component';

describe('Pledge Management Update Component', () => {
  let comp: PledgeUpdateComponent;
  let fixture: ComponentFixture<PledgeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let pledgeFormService: PledgeFormService;
  let pledgeService: PledgeService;
  let projectService: ProjectService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PledgeUpdateComponent],
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
      .overrideTemplate(PledgeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PledgeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    pledgeFormService = TestBed.inject(PledgeFormService);
    pledgeService = TestBed.inject(PledgeService);
    projectService = TestBed.inject(ProjectService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Project query and add missing value', () => {
      const pledge: IPledge = { id: 456 };
      const project: IProject = { id: 39892 };
      pledge.project = project;

      const projectCollection: IProject[] = [{ id: 9665 }];
      jest.spyOn(projectService, 'query').mockReturnValue(of(new HttpResponse({ body: projectCollection })));
      const additionalProjects = [project];
      const expectedCollection: IProject[] = [...additionalProjects, ...projectCollection];
      jest.spyOn(projectService, 'addProjectToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ pledge });
      comp.ngOnInit();

      expect(projectService.query).toHaveBeenCalled();
      expect(projectService.addProjectToCollectionIfMissing).toHaveBeenCalledWith(
        projectCollection,
        ...additionalProjects.map(expect.objectContaining)
      );
      expect(comp.projectsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const pledge: IPledge = { id: 456 };
      const project: IProject = { id: 59516 };
      pledge.project = project;

      activatedRoute.data = of({ pledge });
      comp.ngOnInit();

      expect(comp.projectsSharedCollection).toContain(project);
      expect(comp.pledge).toEqual(pledge);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPledge>>();
      const pledge = { id: 123 };
      jest.spyOn(pledgeFormService, 'getPledge').mockReturnValue(pledge);
      jest.spyOn(pledgeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pledge });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: pledge }));
      saveSubject.complete();

      // THEN
      expect(pledgeFormService.getPledge).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(pledgeService.update).toHaveBeenCalledWith(expect.objectContaining(pledge));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPledge>>();
      const pledge = { id: 123 };
      jest.spyOn(pledgeFormService, 'getPledge').mockReturnValue({ id: null });
      jest.spyOn(pledgeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pledge: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: pledge }));
      saveSubject.complete();

      // THEN
      expect(pledgeFormService.getPledge).toHaveBeenCalled();
      expect(pledgeService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPledge>>();
      const pledge = { id: 123 };
      jest.spyOn(pledgeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pledge });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(pledgeService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareProject', () => {
      it('Should forward to projectService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(projectService, 'compareProject');
        comp.compareProject(entity, entity2);
        expect(projectService.compareProject).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
