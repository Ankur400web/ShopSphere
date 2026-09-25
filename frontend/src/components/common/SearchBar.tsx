import { Search } from "lucide-react";


interface SearchBarProps {
    placeholder?: string;
}

function SearchBar({ placeholder = "Search products..." }: SearchBarProps) {
    return (
        <div className="relative">
            <Search
                className="absolute left-3 top-1/2 -translate-y-1/2 text-text-muted"
                size={18}
            />

            <input
                type="text"
                placeholder={placeholder}
                className="w-full rounded-lg border border-border bg-background py-2 pl-10 pr-4 text-sm text-text-primary outline-none transition-colors focus:border-primary-500"
            />
        </div>
    );
}

export default SearchBar;