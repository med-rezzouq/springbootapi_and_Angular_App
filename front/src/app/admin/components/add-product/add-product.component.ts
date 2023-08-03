import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { NzNotificationService } from 'ng-zorro-antd/notification';
import { AdminService } from '../../admin-services/admin.service';

@Component({
  selector: 'app-add-product',
  templateUrl: './add-product.component.html',
  styleUrls: ['./add-product.component.scss']
})
export class AddProductComponent implements OnInit {

  currentFileUpload: File;
  myFile: FileList;
  avatarUrl: any = 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRMEOb9DSnnU_mwSu5HYXuFuUjktAvKecyMrw&usqp=CAU';
  isSpinning = false;

  constructor(private fb: FormBuilder,
    private notification: NzNotificationService,
    private router: Router,
    private adminService: AdminService) { }

  validateForm!: FormGroup;

  getCaptcha(e: MouseEvent): void {
    e.preventDefault();
  }

  ngOnInit(): void {
    this.validateForm = this.fb.group({
      name: [null, [Validators.required]],
      availableQuantity: [null, [Validators.required]],
      description: [null, [Validators.required]],
      price: [null, [Validators.required]],
    });
  }

  submitForm(): void {
    console.log(this.validateForm.valid);
    console.log(this.validateForm);
    if (this.validateForm.valid) {
      console.log("In function");
      this.isSpinning = true;
      const formData: FormData = new FormData();
      formData.append('img', this.currentFileUpload);
      formData.append('name', this.validateForm.get('name').value);
      formData.append('availableQuantity', this.validateForm.get('availableQuantity').value);
      formData.append('description', this.validateForm.get('description').value);
      formData.append('price', this.validateForm.get('price').value);
      console.log(formData);
      this.adminService.addProduct(formData).subscribe((res) => {
        this.isSpinning = false;
        if (res.id != null) {
          this.notification
            .success(
              'SUCCESS',
              `Produit publié avec succès!`,
              { nzDuration: 5000 }
            );
          this.router.navigateByUrl('/admin/dashboard');
        } else {
          this.notification
            .error(
              'ERROR',
              `${res.message}`,
              { nzDuration: 5000 }
            )
        }
      });
    } else {
      for (const i in this.validateForm.controls) {
        this.validateForm.controls[i].markAsDirty();
        this.validateForm.controls[i].updateValueAndValidity();
      }
    }
  }


  public uploadFile(event: Event): void {
    const input = event.target as HTMLInputElement;
    this.myFile = input.files;

    if (!input.files?.length) {
      return;
    }
    this.currentFileUpload = input.files[0];
    var reader = new FileReader();
    reader.readAsDataURL(this.currentFileUpload);
    reader.onload = (_event) => {
      this.avatarUrl = reader.result;
    };
  }
}
