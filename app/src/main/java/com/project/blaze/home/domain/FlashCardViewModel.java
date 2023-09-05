package com.project.blaze.home.domain;

import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.project.blaze.home.dto.FlashcardModel;
import com.project.blaze.home.repo.FlashcardRepo;

public class FlashCardViewModel extends ViewModel {

    private final FlashcardModel flashcardModel = new FlashcardModel();
    private final MutableLiveData<FlashcardModel> liveUi = new MutableLiveData<>();
    private final MutableLiveData<Uri> liveImgUri = new MutableLiveData<>();
    private final MutableLiveData<Boolean> flashcardSavedLive = new MutableLiveData<>();

    private final FlashcardRepo flashcardRepo = new FlashcardRepo();

    public void setOnSuccessfulListener(FlashcardRepo.OnSuccessfulListener listener)
    {
        flashcardRepo.setListener(listener);
    }

    public void setDeckId(String deckId)
    {
        flashcardRepo.setEmail();
        flashcardRepo.setDeckId(deckId);

    }

    public void setQuestion(String question)
    {
        flashcardModel.setQuestion(question);
    }
    public void setAnswer(String answer)
    {
        flashcardModel.setAnswer(answer);
    }
    public void setMCQ(boolean checked)
    {
        flashcardModel.setMcq(checked);
    }
    public void setLiveImgUri(Uri uri){
        liveImgUri.setValue(uri);
        flashcardModel.setHasImage(true);
    }
    public void setMcqOptions(String mcqOptions)
    {
        flashcardModel.setOptionsList(mcqOptions);
    }

    public void setImage()
    {
        flashcardRepo.uploadImg(liveImgUri.getValue());

    }

    public void setFlashcardSaved()
    {
        flashcardSavedLive.setValue(true);

    }
    public void createFlashcard()
    {
        flashcardRepo.createFlashCard(flashcardModel);
    }

    public LiveData<FlashcardModel> getUiSate()
    {
        liveUi.setValue(flashcardModel);
        return liveUi;
    }

    public String getAnswer()
    {
        return flashcardModel.getAnswer();
    }
    public MutableLiveData<Uri> getLiveImgUri() {
        return liveImgUri;
    }

    public LiveData<Boolean> actionSave()
    {
        return  flashcardSavedLive;
    }
}
