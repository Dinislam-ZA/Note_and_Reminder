package com.example.note_application_neko_ru;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.renderscript.Float3;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.note_application_neko_ru.adapter.RcvItemList;
import com.example.note_application_neko_ru.db.MyConstants;
import com.example.note_application_neko_ru.db.MyDbManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class EditActivity extends AppCompatActivity {
    private final int PICK_IMAGE_CODE = 123;
    private ConstraintLayout ImageContainer;
    private EditText editTitle;
    private EditText editdescription;
    private FloatingActionButton savebutton, addimagebutton;
    private MyDbManager mydbmanager;
    private ImageButton EditImage, DeleteImage;
    private ImageView ImageNote;
    private String URI = "empty";
    private RcvItemList item;
    private boolean IsEditState = true;

    public void init(){
        mydbmanager = new MyDbManager(this);
        ImageContainer = findViewById(R.id.ImageContainer);
        editTitle = findViewById(R.id.edit_title);
        editdescription = findViewById(R.id.ed_description);
        savebutton = findViewById(R.id.add_button);
        addimagebutton = findViewById(R.id.fbAddImage);
        EditImage = findViewById(R.id.EditImageButton);
        DeleteImage = findViewById(R.id.DeleteImageButton);
        ImageNote = findViewById(R.id.imageNote);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE_CODE && data != null){

            getContentResolver().takePersistableUriPermission(data.getData(),Intent.FLAG_GRANT_READ_URI_PERMISSION);
            ImageNote.setImageURI(data.getData());
            URI = data.getData().toString();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        init();
        GetMyIntents();;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mydbmanager.OpenDb();
    }

    public void onClickSave(View view){
        if(InsertValidation()){
            if(!IsEditState == false){
                mydbmanager.InsertToDB(editTitle.getText().toString(),editdescription.getText().toString(),URI);
                Toast.makeText(this, R.string.saved, Toast.LENGTH_SHORT).show();
            }
            else {
                mydbmanager.Update(editTitle.getText().toString(),editdescription.getText().toString(),URI,item.getId());
                Toast.makeText(this, R.string.saved, Toast.LENGTH_SHORT).show();
            }
            finish();
        }
        else{
            Toast.makeText(this, R.string.IOTitleAndDescValid, Toast.LENGTH_SHORT).show();
        }
    }

    public boolean InsertValidation(){
        if(editTitle.getText().toString().equals("")
                || editdescription.getText().toString().equals("")){
            return false;
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mydbmanager.CloseDb();
    }

    public void OnClickAddImage(View view){

        ImageContainer.setVisibility(View.VISIBLE);
        view.setVisibility(View.GONE);
    }

    public void OnClickDeleteImage(View view){
        ImageNote.setImageResource(R.drawable.image_icon);
        URI = "empty";
        ImageContainer.setVisibility(View.GONE);
        addimagebutton.setVisibility(View.VISIBLE);
    }

    public void OnClickEditImage(View view){
        Intent chooser = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        chooser.setType("image/*");
        startActivityForResult(chooser, PICK_IMAGE_CODE);
    }

    private void GetMyIntents(){
        Intent i = getIntent();
        if (i != null){

            item = (RcvItemList) i.getSerializableExtra(MyConstants.LIST_ITEM_INTENT);
            IsEditState = i.getBooleanExtra(MyConstants.EDIT_STATE, true);

            if (IsEditState == false){

                editTitle.setText(item.getTitle());
                editdescription.setText(item.getDescription());
                if (!item.getUri().equals("empty")){
                    URI = item.getUri();
                    ImageContainer.setVisibility(View.VISIBLE);
                    addimagebutton.setVisibility(View.GONE);
                    //EditImage.setVisibility(View.GONE);
                    //DeleteImage.setVisibility(View.GONE);
                    ImageNote.setImageURI(Uri.parse(item.getUri()));
                }
            }
        }

    }
}