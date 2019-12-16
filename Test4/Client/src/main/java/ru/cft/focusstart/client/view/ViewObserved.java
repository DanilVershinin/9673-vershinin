package ru.cft.focusstart.client.view;

import ru.cft.focusstart.common.message.Message;

public interface ViewObserved {
    void update(Message update);
    void updateUsernameList(String[] update);
}
