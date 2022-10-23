package com.radreichley.noctal.module.HN.HNApi

import com.radreichley.noctal.module.HN.models.StoryDto
import com.radreichley.noctal.module.HN.models.StoryType
import kotlinx.coroutines.delay
import kotlinx.datetime.Instant

class HNApiMock : IHNApi {
    companion object {
        val stories = listOf(
            StoryDto(
                "16582136",
                "Stephen Hawking has died",
                "Cogito",
                "http://www.bbc.com/news/uk-43396008",
                Instant.parse("2018-03-14T03:50:30.000Z"),
                6015,
                436,
                StoryType.Story,
                null
            ),
            StoryDto(
                "11116274",
                "A Message to Our Customers",
                "epaga",
                "http://www.apple.com/customer-letter/",
                Instant.parse("2016-02-17T08:38:37.000Z"),
                5771,
                967,
                StoryType.Story,
                null
            ),
            StoryDto(
                "31261533",
                "Mechanical Watch",
                "todsacerdoti",
                "https://ciechanow.ski/mechanical-watch/",
                Instant.parse("2022-05-04T15:06:41.000Z"),
                4298,
                412,
                StoryType.Story,
                null
            ),
            StoryDto(
                "3078128",
                "Steve Jobs has passed away.",
                "patricktomas",
                "http://www.apple.com/stevejobs/",
                Instant.parse("2011-10-05T23:42:23.000Z"),
                4271,
                376,
                StoryType.Story,
                ""
            ),
            StoryDto(
                "24872911",
                "YouTube-dl has received a DMCA takedown from RIAA",
                "phantop",
                "https://github.com/github/dmca/blob/master/2020/10/2020-10-23-RIAA.md",
                Instant.parse("2020-10-23T19:26:35.000Z"),
                4240,
                1411,
                StoryType.Story,
                null
            ),
            StoryDto(
                "13682022",
                "Reflecting on one very, very strange year at Uber",
                "grey-area",
                "https://www.susanjfowler.com/blog/2017/2/19/reflecting-on-one-very-strange-year-at-uber",
                Instant.parse("2017-02-19T21:16:33.000Z"),
                4107,
                1014,
                StoryType.Story,
                null
            ),
            StoryDto(
                "26699106",
                "Google’s copying of the Java SE API was fair use [pdf]",
                "pdoconnell",
                "https://www.supremecourt.gov/opinions/20pdf/18-956_d18f.pdf",
                Instant.parse("2021-04-05T14:04:22.000Z"),
                4103,
                930,
                StoryType.Story,
                null
            ),
            StoryDto(
                "27424195",
                "Replit used legal threats to kill my open-source project",
                "raxod502",
                "https://intuitiveexplanations.com/tech/replit/",
                Instant.parse("2021-06-07T16:08:04.000Z"),
                4022,
                1274,
                StoryType.Story,
                null
            ),
            StoryDto(
                "26296339",
                "How I cut GTA Online loading times by 70%",
                "kuroguro",
                "https://nee.lv/2021/02/28/How-I-cut-GTA-Online-loading-times-by-70/",
                Instant.parse("2021-02-28T19:38:26.000Z"),
                3883,
                697,
                StoryType.Story,
                null
            ),
            StoryDto(
                "23065782",
                "Bye, Amazon",
                "grey-area",
                "https://www.tbray.org/ongoing/When/202x/2020/04/29/Leaving-Amazon",
                Instant.parse("2020-05-04T08:43:35.000Z"),
                3816,
                1097,
                StoryType.Story,
                null
            ),
            StoryDto(
                "30347719",
                "Google Search Is Dying",
                "dbrereton",
                "https://dkb.io/post/google-search-is-dying",
                Instant.parse("2022-02-15T15:29:20.000Z"),
                3636,
                1561,
                StoryType.Story,
                null
            ),
            StoryDto(
                "22107823",
                "Every Google result now looks like an ad",
                "cmod",
                "https://twitter.com/craigmod/status/1219644556003565568",
                Instant.parse("2020-01-21T15:38:22.000Z"),
                3592,
                969,
                StoryType.Story,
                null
            ),
            StoryDto(
                "28550764",
                "A search engine that favors text-heavy sites and punishes modern web design",
                "Funes-",
                "https://search.marginalia.nu/",
                Instant.parse("2021-09-16T12:16:14.000Z"),
                3441,
                717,
                StoryType.Story,
                null
            ),
            StoryDto(
                "29845208",
                "My First Impressions of Web3",
                "natdempk",
                "https://moxie.org/2022/01/07/web3-first-impressions.html",
                Instant.parse("2022-01-07T21:41:56.000Z"),
                3393,
                1129,
                StoryType.Story,
                null
            ),
            StoryDto(
                "15924794",
                "F.C.C. Repeals Net Neutrality Rules",
                "panny",
                "https://www.nytimes.com/2017/12/14/technology/net-neutrality-repeal-vote.html",
                Instant.parse("2017-12-14T18:13:35.000Z"),
                3384,
                1397,
                StoryType.Story,
                null
            ),
            StoryDto(
                "3742902",
                "Show HN: This up votes itself",
                "olalonde",
                "http://news.ycombinator.com/vote?for=3742902&dir=up&whence=%6e%65%77%65%73%74",
                Instant.parse("2012-03-23T00:40:39.000Z"),
                3381,
                83,
                StoryType.ShowHn,
                ""
            ),
            StoryDto(
                "26487854",
                "GitHub, fuck your name change",
                "leontrolski",
                "https://mooseyanon.medium.com/github-f-ck-your-name-change-de599033bbbe",
                Instant.parse("2021-03-17T08:11:38.000Z"),
                3353,
                2010,
                StoryType.Story,
                null
            ),
            StoryDto(
                "20052623",
                "Switch from Chrome to Firefox",
                "WisNorCan",
                "https://www.mozilla.org/en-US/firefox/switch/",
                Instant.parse("2019-05-30T16:09:19.000Z"),
                3287,
                981,
                StoryType.Story,
                null
            ),
            StoryDto(
                "22918980",
                "Ask HN: I'm a software engineer going blind, how should I prepare?",
                "zachrip",
                null,
                Instant.parse("2020-04-19T21:33:46.000Z"),
                3270,
                473,
                StoryType.AskHn,
                "I&#x27;m a 24 y&#x2F;o full stack engineer (I know some of you are rolling your eyes right now, just highlighting that I have experience on frontend apps as well as backend architecture). I&#x27;ve been working professionally for ~7 years building mostly javascript projects but also some PHP. Two years ago I was diagnosed with a condition called &quot;Usher&#x27;s Syndrome&quot; - characterized by hearing loss, balance issues, and progressive vision loss.<p>I know there are blind software engineers out there. My main questions are:<p>- Are there blind frontend engineers?<p>- What kinds of software engineering lend themselves to someone with limited vision? Backend only?<p>- Besides a screen reader, what are some of the best tools for building software with limited vision?<p>- Does your company employ blind engineers? How well does it work? What kind of engineer are they?<p>I&#x27;m really trying to get ahead of this thing and prepare myself as my vision is degrading rather quickly. I&#x27;m not sure what I can do if I can&#x27;t do SE as I don&#x27;t have any formal education in anything. I&#x27;ve worked really hard to get to where I am and don&#x27;t want it to go to waste.<p>Thank you for any input, and stay safe out there!<p>Edit:<p>Thank you all for your links, suggestions, and moral support, I really appreciate it. Since my diagnosis I&#x27;ve slowly developed a crippling anxiety centered around a feeling that I need to figure out the rest of my life before it&#x27;s too late. I know I shouldn&#x27;t think this way but it is hard not to. I&#x27;m very independent and I feel a pressure to &quot;show up.&quot; I will look into these opportunities mentioned and try to get in touch with some more members of the blind engineering community."
            ),
            StoryDto(
                "13718752",
                "Cloudflare Reverse Proxies Are Dumping Uninitialized Memory",
                "tptacek",
                "https://bugs.chromium.org/p/project-zero/issues/detail?id=1139",
                Instant.parse("2017-02-23T23:05:08.000Z"),
                3238,
                992,
                StoryType.Story,
                null
            ),
        )
    }

    override suspend fun getStoriesAsync(): List<StoryDto> {
        delay(2000)

        return stories
    }
}