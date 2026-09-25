interface IconButtonProps {
    label: string;
    children: React.ReactNode;
}

function IconButton({ label, children }: IconButtonProps) {
    return (
        <button
            type="button"
            aria-label={label}
            className="rounded-lg p-2 text-text-secondary transition-colors hover:bg-background hover:text-primary-600"
        >
            {children}
        </button>
    );
}

export default IconButton;