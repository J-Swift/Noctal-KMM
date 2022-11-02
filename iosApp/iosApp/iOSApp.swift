import SwiftUI
import shared

@main
struct iOSApp: App {
    init() {
        MetaFetcher.companion.engine = IosEngine()
    }
    
    var body: some Scene {
        WindowGroup {
            AppWrapper()
        }
    }
}

struct AppWrapper : View {
    @Environment(\.colorScheme) var scheme
    
    var body: some View {
        AppLayout()
            .environment(\.noctalTheme, scheme == .light ? LightTheme() : DarkTheme())
    }
}

private struct NoctalThemeKey: EnvironmentKey {
    static let defaultValue = DarkTheme() as ITheme
}

extension EnvironmentValues {
    var noctalTheme: ITheme {
        get { self[NoctalThemeKey.self] }
        set { self[NoctalThemeKey.self] = newValue }
    }
}

class IosEngine : RegexEngine
{
    var patternCache: Dictionary<String, NSRegularExpression> = Dictionary<String, NSRegularExpression>()
    
    func isMatch(pattern: String, target: String) -> Bool {
        guard let regex = getRegex(pattern: pattern) else {
            return false
        }
        
        let res = regex.firstMatch(in: target, range: NSRange(target.startIndex..., in: target)) != nil
        return res
    }
    
    func matchesFor(pattern: String, target: String) -> [String] {
        let x = getMatches(regex: pattern, inputText: target, matchGroup: 0)
        return x
    }
    
    func valueFor(pattern: String, target: String, groupNumber: KotlinInt?) -> String? {
        let matches = getMatches(regex: pattern, inputText: target, matchGroup: groupNumber?.intValue ?? 0)
        guard !matches.isEmpty else {
            return nil
        }
        return matches[0]
    }
    
    private func getRegex(pattern: String) -> NSRegularExpression? {
        var regex = patternCache[pattern]
        
        if regex == nil {
            regex = try? NSRegularExpression(pattern: pattern, options: .caseInsensitive)
        }
        
        return regex
    }
    
    private func getMatches(regex: String, inputText: String, matchGroup: Int) -> [String] {
        guard let regex = getRegex(pattern: regex) else {
            return []
        }
        let results = regex.matches(in: inputText,
                                    range: NSRange(inputText.startIndex..., in: inputText))
        
        let finalResult = results.map { match in
            return (0..<match.numberOfRanges).map { range -> String in
                
                let rangeBounds = match.range(at: range)
                guard let range = Range(rangeBounds, in: inputText) else {
                    return ""
                }
                return String(inputText[range])
            }
        }.filter { !$0.isEmpty }
        
        var allMatches: [String] = []
        
        // Iterate over the final result which includes all the matches and groups
        // We will store all the matching strings
        for result in finalResult {
            for (index, resultText) in result.enumerated() {
                
                // Skip the match. Go to the next elements which represent matching groups
                if index != matchGroup {
                    continue
                }
                allMatches.append(resultText)
            }
        }
        
        return allMatches
    }
}
