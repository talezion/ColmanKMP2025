import Foundation

struct Review: Identifiable {
    let id: Int
    let author: String
    let content: String
    let createdAt: Date
    let rating: Double?
}
