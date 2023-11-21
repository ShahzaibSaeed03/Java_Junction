package com.example.javajunction.fargments;

import static android.app.Activity.RESULT_OK;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.javajunction.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

public class My_Profile extends Fragment {

    private ImageView profileEdit;
    private StorageReference storageReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my__profile, container, false);

        // Initialize storageReference
        storageReference = FirebaseStorage.getInstance().getReference();

        profileEdit = view.findViewById(R.id.Profile_image);

        profileEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageChooserDialog();
            }

            private void showImageChooserDialog() {
                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                builder.setTitle("Choose Image Source");
                builder.setItems(new CharSequence[]{"Camera", "Gallery"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int requestCode = (which == 0) ? 124 : 123; // 124 for camera, 123 for gallery
                        imageChooser(requestCode);
                    }
                });
                builder.show();
            }

            private void imageChooser(int requestCode) {
                Intent intent;
                if (requestCode == 124) {
                    // Capture image from camera
                    intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (intent.resolveActivity(requireActivity().getPackageManager()) != null) {
                        startActivityForResult(intent, requestCode);
                    }
                } else {
                    // Choose image from gallery
                    intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), requestCode);
                }
            }
        });

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference root = db.getReference().child("User Name");

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null) {
            String uid = currentUser.getUid();

            TextView emailTextView = view.findViewById(R.id.Email_profile);
            emailTextView.setText(currentUser.getEmail());

            root.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    DataSnapshot userSnapshot = dataSnapshot.child(uid);

                    if (userSnapshot.exists()) {
                        String userName = userSnapshot.child("Name").getValue(String.class);
                        String userAddress = userSnapshot.child("Address").getValue(String.class);
                        Long userMobileNumber = userSnapshot.child("Number").getValue(Long.class);
                        String mobileNumberString = (userMobileNumber != null) ? String.valueOf(userMobileNumber) : "";

                        TextView nameTextView = view.findViewById(R.id.Name_profile);
                        TextView addressTextView = view.findViewById(R.id.Address_profile);
                        TextView mobileTextView = view.findViewById(R.id.Mobile_profile);

                        nameTextView.setText(userName);
                        addressTextView.setText(userAddress);
                        mobileTextView.setText(mobileNumberString);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle error
                }
            });

            // Load the profile image from Firebase Storage
            loadProfileImage(uid);
        }

        ImageView genderImage = view.findViewById(R.id.Gender_profiles);
//        genderImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showGenderSelectionDialog();
//            }
//        });

        return view;
    }

//    private void showGenderSelectionDialog() {
//        final Dialog dialog = new Dialog(requireContext());
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setCancelable(true);
//        dialog.setContentView(R.layout.dialog_gender_selection);
//
//        final RadioGroup radioGroup = dialog.findViewById(R.id.radioGroup);
//        final Button saveGenderButton = dialog.findViewById(R.id.saveGenderButton);
//
//        saveGenderButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();
//                String selectedGender = getSelectedGender(selectedRadioButtonId);
//
//                if (selectedGender != null) {
//                    TextView genderProfile = getView().findViewById(R.id.Gender_profile);
//                    genderProfile.setText(selectedGender);
//
//                    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
//                    if (currentUser != null) {
//                        String uid = currentUser.getUid();
//                        // Save the selected gender in the database
//                        saveGenderInDatabase(uid, selectedGender);
//                    }
//
//                    dialog.dismiss();
//                } else {
//                    // Handle the case where no option is selected
//                    // You can show an error message or handle it as needed
//                }
//            }
//        });
//
//        dialog.show();
//    }

    // Add this method to your class
//    private void saveGenderInDatabase(String uid, String selectedGender) {
//        // Save the selected gender in the 'gender' node under the user's UID in the 'User Name' node
//        DatabaseReference genderRef = FirebaseDatabase.getInstance().getReference().child("User Name").child(uid).child("gender");
//        genderRef.setValue(selectedGender);
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 123 && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            profileEdit.setImageURI(uri);
            uploadImageToFirebase(uri);
        } else if (requestCode == 124 && resultCode == RESULT_OK && data != null) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            profileEdit.setImageBitmap(imageBitmap);

            // Convert the Bitmap to Uri
            Uri uri = getImageUri(requireContext(), imageBitmap);
            uploadImageToFirebase(uri);
        }
    }

    private Uri getImageUri(Context context, Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

        String path = MediaStore.Images.Media.insertImage(
                context.getContentResolver(),
                bitmap,
                "ImageTitle",
                null
        );

        return Uri.parse(path);
    }

    private void uploadImageToFirebase(Uri imageUri) {
        if (imageUri != null) {
            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            if (currentUser != null) {
                String uid = currentUser.getUid();
                StorageReference imageRef = storageReference.child("profile_images").child(uid);

                imageRef.putFile(imageUri)
                        .addOnSuccessListener(taskSnapshot -> {
                            // Image uploaded successfully
                            // You can get the download URL if needed:
                            imageRef.getDownloadUrl().addOnSuccessListener(downloadUrl -> {
                                // Now, you can save the download URL in your database if necessary
                                String imageUrl = downloadUrl.toString();
                                saveImageUrlInDatabase(uid, imageUrl);
                            });
                        })
                        .addOnFailureListener(e -> {
                            // Handle any errors that occurred during the upload
                            e.printStackTrace();
                            // Display an error message or handle it as needed
                        });
            }
        }
    }

    private void loadProfileImage(String uid) {
        // Assuming 'uid' is the key for the user in the 'User Name' node
        DatabaseReference imageUrlRef = FirebaseDatabase.getInstance().getReference().child("User Name").child(uid).child("imageUrl");

        imageUrlRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String imageUrl = dataSnapshot.getValue(String.class);
                    // Now you have the image URL, you can use it to load the image into your ImageView
                    loadImageFromUrl(imageUrl);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });
    }

    private void saveImageUrlInDatabase(String uid, String imageUrl) {
        // Save the image URL in the 'imageUrl' node under the user's UID in the 'User Name' node
        DatabaseReference imageUrlRef = FirebaseDatabase.getInstance().getReference().child("User Name").child(uid).child("imageUrl");
        imageUrlRef.setValue(imageUrl);
    }

    private void loadImageFromUrl(String imageUrl) {
        // Use Picasso or Glide to load the image into the ImageView
        Picasso.get().load(imageUrl).into(profileEdit);
    }


}
