import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { AssociationService } from '../service/association.service';

import { AssociationComponent } from './association.component';

describe('Association Management Component', () => {
  let comp: AssociationComponent;
  let fixture: ComponentFixture<AssociationComponent>;
  let service: AssociationService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'association', component: AssociationComponent }]), HttpClientTestingModule],
      declarations: [AssociationComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            data: of({
              defaultSort: 'id,asc',
            }),
            queryParamMap: of(
              jest.requireActual('@angular/router').convertToParamMap({
                page: '1',
                size: '1',
                sort: 'id,desc',
              })
            ),
            snapshot: { queryParams: {} },
          },
        },
      ],
    })
      .overrideTemplate(AssociationComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AssociationComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(AssociationService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.associations?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to associationService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getAssociationIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getAssociationIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
