
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';



// @angular/material and dependencies
// import { MAT_MOMENT_DATE_FORMATS, MomentDateAdapter } from '@angular/material-moment-adapter';
// import { DateAdapter, MAT_DATE_FORMATS, MAT_DATE_LOCALE } from '@angular/material/core';

import { MatButtonModule
, MatNativeDateModule
, MatDatepickerModule
, MatRadioModule
, MatTabsModule
, MatInputModule
, MatExpansionModule
, MatCardModule
, MatToolbarModule
, MatProgressSpinnerModule
, MatProgressBarModule
, MatListModule
, MatSelectModule
, MatPaginatorModule
, MatSidenavModule
, MatCheckboxModule
, MatTooltipModule
, MatSlideToggleModule
, MatChipsModule
, MatAutocompleteModule
, MatIconModule
, MatSnackBarModule
, MatSpinner
, MatSortModule
, MatTableModule
} from '@angular/material';

const MATERIAL_MODULES: any[] = [
	MatButtonModule
	, MatNativeDateModule
	, MatDatepickerModule
	, MatRadioModule
	, MatTabsModule
	, MatInputModule
	, MatCardModule
	, MatExpansionModule
	, MatToolbarModule
	, MatProgressSpinnerModule
	, MatProgressBarModule
	, MatListModule
	, MatSelectModule
	, MatPaginatorModule
	, MatSidenavModule
	, MatCheckboxModule
	, MatTooltipModule
	, MatSlideToggleModule
	, MatChipsModule
	, MatAutocompleteModule
	, MatIconModule
	, MatSnackBarModule
	, MatSortModule
	, MatTableModule
];

// const CUSTOM_MOMENT_FORMATS = {
// 	parse: { dateInput: "mm/dd/yyyy" },
// 	display: MAT_MOMENT_DATE_FORMATS.display
// };

@NgModule({
	imports: [
		CommonModule
		, ...MATERIAL_MODULES
	],
	providers: [
	],
	declarations: [

	],
	exports: [
		CommonModule,
		 ...MATERIAL_MODULES,
		 MatSpinner

	],
})
export class EpSharedModule { }
