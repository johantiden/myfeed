package se.johantiden.myfeed.plugin;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class SlashdotFeedTest {


    @Test
    public void testName() throws Exception {




        String html = "An anonymous reader shares an article: For generations, movies, video games, and tv shows have portrayed the developer as either an awkward hoodie-wearing nerd, or an insane and menacing basement dweller (or both). From Ace Ventura to Silicon Valley, everyone has had their chance to portray the developer. Few actors do this with the same grace they'd reserve for a role portraying a doctor. [...] I think it's time for all of us to try and elevate our understanding of what a developer is. If you are a tech company who markets to developers, or is hoping to hire developers this is doubly true. So, how should we talk about developers? First, we should talk about how important their work is. Programming is one of the fastest growing industries in the world as it serves a role in every part of society. Developers maintain and build critical parts of our infrastructure. Second, we need to talk about the craft of what they do... we need to show more code. Every developer may use a different set of tools, but across the board their craft is evolving at increasing rates. [...] I think we can drop developer stereotypes all together at this point. It's a job people know -- it's time to add some vitamins to that kool-aid. After all, we're just like lawyers, librarians, electricians and cab drivers... we're just people, totally unique and different people. But if there is one thing that unites us, it's a unifying desire to build new things, improve old things, learn when we can and avoid being stereotyped. It's as simple as that.<p><div class=\"share_submission\" style=\"position:relative;\">\n" +
                "<a class=\"slashpop\" href=\"http://twitter.com/home?status=More+Than+a+Hoodie%3A+How+We+Talk+About+Developers%3A+http%3A%2F%2Fbit.ly%2F2nGMFNF\"><img src=\"https://a.fsdn.com/sd/twitter_icon_large.png\"></a>\n" +
                "<a class=\"slashpop\" href=\"http://www.facebook.com/sharer.php?u=https%3A%2F%2Fdevelopers.slashdot.org%2Fstory%2F17%2F04%2F04%2F1913238%2Fmore-than-a-hoodie-how-we-talk-about-developers%3Futm_source%3Dslashdot%26utm_medium%3Dfacebook\"><img src=\"https://a.fsdn.com/sd/facebook_icon_large.png\"></a>\n" +
                "\n" +
                "<a class=\"nobg\" href=\"http://plus.google.com/share?url=https://developers.slashdot.org/story/17/04/04/1913238/more-than-a-hoodie-how-we-talk-about-developers?utm_source=slashdot&amp;utm_medium=googleplus\" onclick=\"javascript:window.open(this.href,'', 'menubar=no,toolbar=no,resizable=yes,scrollbars=yes,height=600,width=600');return false;\"><img src=\"http://www.gstatic.com/images/icons/gplus-16.png\" alt=\"Share on Google+\"/></a>                                                                                                                                                                              \n" +
                "\n" +
                "\n" +
                "\n" +
                "</div></p><p><a href=\"https://developers.slashdot.org/story/17/04/04/1913238/more-than-a-hoodie-how-we-talk-about-developers?utm_source=atom1.0moreanon&amp;utm_medium=feed\">Read more of this story</a> at Slashdot.</p>";


        String prune = SlashdotFeed.prune(html);
        String expected = "An anonymous reader shares an article: For generations, movies, video games, and tv shows have portrayed the developer as either an awkward hoodie-wearing nerd, or an insane and menacing basement dweller (or both). From Ace Ventura to Silicon Valley, everyone has had their chance to portray the developer. Few actors do this with the same grace they'd reserve for a role portraying a doctor. [...] I think it's time for all of us to try and elevate our understanding of what a developer is. If you are a tech company who markets to developers, or is hoping to hire developers this is doubly true. So, how should we talk about developers? First, we should talk about how important their work is. Programming is one of the fastest growing industries in the world as it serves a role in every part of society. Developers maintain and build critical parts of our infrastructure. Second, we need to talk about the craft of what they do... we need to show more code. Every developer may use a different set of tools, but across the board their craft is evolving at increasing rates. [...] I think we can drop developer stereotypes all together at this point. It's a job people know -- it's time to add some vitamins to that kool-aid. After all, we're just like lawyers, librarians, electricians and cab drivers... we're just people, totally unique and different people. But if there is one thing that unites us, it's a unifying desire to build new things, improve old things, learn when we can and avoid being stereotyped. It's as simple as that.";
        assertThat(prune, is(expected));

    }
}