/*
 * Copyright (C) 2016 yydcdut (yuyidong2015@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.yydcdut.rxmarkdown.chain;

import android.support.annotation.NonNull;

import com.yydcdut.rxmarkdown.syntax.Syntax;

import java.util.Arrays;

/**
 * This Chain can add one or more syntaxs.
 * <p>
 * Created by yuyidong on 16/5/4.
 */
public class MultiGrammarsChain implements IChain {
    private Syntax[] mGrammars;

    private IChain mNextHandleGrammar = null;

    /**
     * Constructor
     *
     * @param grammars the syntax
     */
    public MultiGrammarsChain(@NonNull Syntax... grammars) {
        mGrammars = grammars;
    }

    @NonNull
    @Override
    public boolean handleGrammar(@NonNull CharSequence charSequence) {
        for (Syntax syntax : mGrammars) {
            if (syntax.isMatch(charSequence)) {
                syntax.format(charSequence);
            }
        }
        if (mNextHandleGrammar != null) {
            return mNextHandleGrammar.handleGrammar(charSequence);
        } else {
            return false;
        }
    }

    /**
     * @param nextHandleGrammar the next chain
     * @return boolean
     * @deprecated
     */
    @Override
    @Deprecated
    public boolean addNextHandleGrammar(@NonNull IChain nextHandleGrammar) {
        return false;
    }

    @Override
    public boolean setNextHandleGrammar(@NonNull IChain nextHandleGrammar) {
        mNextHandleGrammar = nextHandleGrammar;
        return true;
    }

    @Override
    public String toString() {
        return "MultiGrammarsChain{" +
                "mGrammars=" + Arrays.toString(mGrammars) +
                ", mNextHandleGrammar=" + mNextHandleGrammar +
                '}';
    }
}
