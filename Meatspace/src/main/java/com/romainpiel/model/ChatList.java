package com.romainpiel.model;

import java.util.Collection;
import java.util.Comparator;
import java.util.TreeSet;

/**
 * Meatspace
 * User: romainpiel
 * Date: 01/11/2013
 * Time: 17:37
 */
public class ChatList {

    private Content chats;

    public ChatList() {
        this(null);
    }

    public ChatList(Collection<Chat> chats) {
        this.chats = new Content();

        if (chats != null) {
            this.chats.addAll(chats);
        }
    }

    public TreeSet<Chat> get() {
        return chats != null? chats.get() : null;
    }

    public void addAll(TreeSet<Chat> items) {
        if (chats != null && chats.get() != null) {
            chats.get().addAll(items);
        }
    }

    public void clear() {
        if (chats != null && chats.get() != null) {
            chats.get().clear();
        }
    }

    private static class Content {
        private TreeSet<Chat> chats;

        public Content() {
            chats = new TreeSet<Chat>(new Comparator<Chat>() {
                @Override
                public int compare(Chat lhs, Chat rhs) {
                    return Chat.compare(lhs, rhs);
                }
            });
        }

        public TreeSet<Chat> get() {
            return chats;
        }

        public void addAll(Collection<Chat> items) {
            chats.addAll(items);
        }
    }
}
