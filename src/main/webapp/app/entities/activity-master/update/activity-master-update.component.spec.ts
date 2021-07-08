jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { ActivityMasterService } from '../service/activity-master.service';
import { IActivityMaster, ActivityMaster } from '../activity-master.model';

import { ActivityMasterUpdateComponent } from './activity-master-update.component';

describe('Component Tests', () => {
  describe('ActivityMaster Management Update Component', () => {
    let comp: ActivityMasterUpdateComponent;
    let fixture: ComponentFixture<ActivityMasterUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let activityMasterService: ActivityMasterService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [ActivityMasterUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(ActivityMasterUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ActivityMasterUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      activityMasterService = TestBed.inject(ActivityMasterService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const activityMaster: IActivityMaster = { id: 456 };

        activatedRoute.data = of({ activityMaster });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(activityMaster));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<ActivityMaster>>();
        const activityMaster = { id: 123 };
        jest.spyOn(activityMasterService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ activityMaster });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: activityMaster }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(activityMasterService.update).toHaveBeenCalledWith(activityMaster);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<ActivityMaster>>();
        const activityMaster = new ActivityMaster();
        jest.spyOn(activityMasterService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ activityMaster });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: activityMaster }));
        saveSubject.complete();

        // THEN
        expect(activityMasterService.create).toHaveBeenCalledWith(activityMaster);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<ActivityMaster>>();
        const activityMaster = { id: 123 };
        jest.spyOn(activityMasterService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ activityMaster });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(activityMasterService.update).toHaveBeenCalledWith(activityMaster);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
