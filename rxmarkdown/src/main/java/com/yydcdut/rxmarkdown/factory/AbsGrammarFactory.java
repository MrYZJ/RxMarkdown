package com.yydcdut.rxmarkdown.factory;

import android.support.annotation.NonNull;

import com.yydcdut.rxmarkdown.chain.GrammarDoElseChain;
import com.yydcdut.rxmarkdown.chain.GrammarMultiChains;
import com.yydcdut.rxmarkdown.chain.GrammarSingleChain;
import com.yydcdut.rxmarkdown.chain.IChain;
import com.yydcdut.rxmarkdown.chain.MultiGrammarsChain;
import com.yydcdut.rxmarkdown.grammar.IGrammar;

/**
 * Created by yuyidong on 16/5/12.
 */
public abstract class AbsGrammarFactory {
    protected IChain mLineChain;
    protected IChain mTotalChain;

    public AbsGrammarFactory() {
        mTotalChain = new GrammarSingleChain(getCodeGrammar());
        mLineChain = new GrammarSingleChain(getHorizontalRulesGrammar());
        GrammarDoElseChain blockQuitesChain = new GrammarDoElseChain(getBlockQuotesGrammar());
        GrammarDoElseChain todoChain = new GrammarDoElseChain(getTodoGrammar());
        GrammarDoElseChain todoDoneChain = new GrammarDoElseChain(getTodoDoneGrammar());
        GrammarDoElseChain orderListChain = new GrammarDoElseChain(getOrderListGrammar());
        GrammarDoElseChain unOrderListChain = new GrammarDoElseChain(getUnOrderListGrammar());
        GrammarMultiChains centerAlignChain = new GrammarMultiChains(getCenterAlignGrammar());
        GrammarMultiChains headerChain = new GrammarMultiChains(getHeaderGrammar());
        MultiGrammarsChain multiChain = new MultiGrammarsChain(
                getBoldGrammar(),
                getItalicGrammar(),
                getInlineCodeGrammar(),
                getStrikeThroughGrammar(),
                getFootnoteGrammar(),
                getImageGrammar(),
                getHyperLinkGrammar());

        mLineChain.setNextHandleGrammar(blockQuitesChain);

        blockQuitesChain.setNextHandleGrammar(todoChain);
        blockQuitesChain.addNextHandleGrammar(multiChain);

        todoChain.setNextHandleGrammar(todoDoneChain);
        todoChain.addNextHandleGrammar(multiChain);

        todoDoneChain.setNextHandleGrammar(orderListChain);
        todoDoneChain.addNextHandleGrammar(multiChain);

        orderListChain.setNextHandleGrammar(unOrderListChain);
        orderListChain.addNextHandleGrammar(multiChain);

        unOrderListChain.setNextHandleGrammar(centerAlignChain);
        unOrderListChain.addNextHandleGrammar(multiChain);

        centerAlignChain.addNextHandleGrammar(headerChain);
        centerAlignChain.addNextHandleGrammar(multiChain);

        headerChain.addNextHandleGrammar(multiChain);
    }

    protected abstract IGrammar getHorizontalRulesGrammar();

    protected abstract IGrammar getBlockQuotesGrammar();

    protected abstract IGrammar getTodoGrammar();

    protected abstract IGrammar getTodoDoneGrammar();

    protected abstract IGrammar getOrderListGrammar();

    protected abstract IGrammar getUnOrderListGrammar();

    protected abstract IGrammar getCenterAlignGrammar();

    protected abstract IGrammar getHeaderGrammar();

    protected abstract IGrammar getBoldGrammar();

    protected abstract IGrammar getItalicGrammar();

    protected abstract IGrammar getInlineCodeGrammar();

    protected abstract IGrammar getStrikeThroughGrammar();

    protected abstract IGrammar getFootnoteGrammar();

    protected abstract IGrammar getImageGrammar();

    protected abstract IGrammar getHyperLinkGrammar();

    protected abstract IGrammar getCodeGrammar();

    @NonNull
    public abstract CharSequence parse(@NonNull CharSequence charSequence);
}
